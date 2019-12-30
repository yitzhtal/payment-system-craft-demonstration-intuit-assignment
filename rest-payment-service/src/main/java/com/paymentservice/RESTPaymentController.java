package com.paymentservice;

import beans.Payee;
import beans.Payment;
import beans.PaymentMethod;
import com.google.gson.Gson;
import enums.PaymentMethodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.RabbitMQService;
import utils.RESTPaymentControllerUtils;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/payment-service")
public class RESTPaymentController {

    @Autowired
    RESTPaymentControllerUtils restPaymentControllerUtils;

    @Autowired
    LoggingController logger;

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    private Gson gson;

    @PostMapping(value="/create-payment", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createPayment(@RequestParam("amount") String amount, @RequestParam("currency") String currency, @RequestParam("userId") String userId, @RequestParam("payeeId") String payeeId,
                                                       @RequestParam("paymentMethodId") String paymentMethodId) {
        logger.info("createPayment() - was called");

        logger.info("createPayment() - verifying all fields are not empty");
        if(!restPaymentControllerUtils.isAllFieldsNotEmpty(amount,currency,userId,payeeId,paymentMethodId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide all mandatory inputs.");
        }

        logger.info("createPayment() - verifying the currency received is supported");
        if(!restPaymentControllerUtils.isSupportedCurrency(currency)) {
            return new ResponseEntity<>("The currency you entered is not valid.", HttpStatus.BAD_REQUEST);
        }

        logger.info("createPayment() - verifying the payment methods is valid");
        if(!restPaymentControllerUtils.isPaymentMethodValid(paymentMethodId)) {
            return ResponseEntity.badRequest().body("The payment method you entered is not valid.");
        }

        logger.info("createPayment() - verifying the payment amount is valid");
        if(!restPaymentControllerUtils.isStringContainsOnlyNumbers(amount)) {
            return ResponseEntity.badRequest().body("The payment amount you entered is not valid.");

        }

        Payment payment = new Payment(amount,currency, userId, payeeId,  paymentMethodId);

        // For simplicity, this is only POC - I'm generating new UUID for every request
        // In a large scale system, there should be a database that maps users to their UUIDs and for every new user -> should generate it's unique UUID
        // For existing user -> it's UUID should be taken from this Database
        // Example, for the following mapping:
        // Tal Yitzhak -> 2489f48a-aa32-4539-9c24-a320f9fa0e38
        // Mor Basson -> e9fabbe3-1907-4978-960d-5edfb1733da7
        // If Nir Harel is sending a request, as a new user he will be added to the Database: Nir Harel -> 9f871606-5dd4-4996-8076-00ddf9d33007.
        // In the next payment Nir Harel is performing, we use the value '9f871606-5dd4-4996-8076-00ddf9d33007' from the DB.

        payment.setUserId(UUID.randomUUID().toString());
        payment.setPayeeId(UUID.randomUUID().toString());
        payment.setPaymentMethodId(UUID.randomUUID().toString());

        try {
            logger.info("createPayment() - publishing the message");
            rabbitMQService.publishMessage(gson.toJson(payment));
        } catch(Exception e) {
            logger.info("createPayment() -  failed in connecting to services");
            return new ResponseEntity<>("Connection failure. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("createPayment() - ended successfully");

        return new ResponseEntity<>("Your payment was sent to our system and is verified now. " +
                "You will accept an email soon with payment confirmation. Thanks for choosing Intuit's Payment System.", HttpStatus.OK);
    }

    @GetMapping(value = "/payment-methods", produces={"application/json"})
    public @ResponseBody ArrayList<PaymentMethod> getAllPaymentMethods() throws URISyntaxException {
        logger.info("getAllPaymentMethods() was called");
        PaymentMethod first = new PaymentMethod(PaymentMethodType.BANK_ACCOUNT, "Chace Checking Account","*1567");
        ArrayList<PaymentMethod> allPaymentMethods = new ArrayList<PaymentMethod>();
        allPaymentMethods.add(first);
        logger.info("getAllPaymentMethods() ended successfully");
        return allPaymentMethods;
    }

    @GetMapping(value = "/payees", produces={"application/json"})
    public @ResponseBody ArrayList<Payee> getAllPayees() {
        logger.info("getAllPayees() was called");
        Payee first = new Payee("jonh.smith@gmail.com", "Jonh Smith");
        ArrayList<Payee> allPayees = new ArrayList<Payee>();
        allPayees.add(first);
        logger.info("getAllPayees() ended successfully");
        return allPayees;
    }
}
