package com.paymentservice;

import beans.Payee;
import beans.Payment;
import beans.PaymentMethod;
import com.google.gson.Gson;
import enums.PaymentMethodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import services.RabbitMQService;
import utils.RESTPaymentControllerUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping(value="/payment-service")
@CrossOrigin("http://localhost:8080")
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "payments")
@Path("/payment-service")
@Controller
public class RESTPaymentController {

    @Autowired
    RESTPaymentControllerUtils restPaymentControllerUtils;

    @Autowired
    LoggingController logger;

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    Gson gson;


    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/create-payment")
    @PostMapping(value="/create-payment", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public Response createPayment(@FormParam("amount") String amount, @FormParam("currency") String currency, @FormParam("userId") String userId, @FormParam("payeeId") String payeeId,
                                  @FormParam("paymentMethodId") String paymentMethodId) {
        logger.info("createPayment() - was called");

        logger.info("createPayment() - verifying all fields are not empty");
        if(!restPaymentControllerUtils.isAllFieldsNotEmpty(amount,currency,userId,payeeId,paymentMethodId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please provide all mandatory inputs.").build();
        }

        logger.info("createPayment() - verifying the currency received is supported");
        if(!restPaymentControllerUtils.isSupportedCurrency(currency)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("The currency you entered is not supported at the moment.").build();
        }

        logger.info("createPayment() - verifying the payment methods is valid");
        if(!restPaymentControllerUtils.isPaymentMethodValid(paymentMethodId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The payment method you entered is not valid.").build();
        }

        logger.info("createPayment() - verifying the payment amount is valid");
        if(!restPaymentControllerUtils.isStringContainsOnlyNumbers(amount)) {
            return Response.status(Response.Status.BAD_REQUEST).entity("The payment amount you entered is not valid.").build();
        }

        Payment payment = new Payment(amount,currency, userId, payeeId,  paymentMethodId);

        //check this GUID
        payment.setUserId(UUID.randomUUID().toString());
        payment.setPayeeId(UUID.randomUUID().toString());
        payment.setPaymentMethodId(UUID.randomUUID().toString());

        try {
            logger.info("createPayment() - publishing the message");
            rabbitMQService.publishMessage(gson.toJson(payment));
        } catch(Exception e) {
            logger.info("createPayment() -  failed in connecting to services");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection failure. Please try again.").build();
        }

        logger.info("createPayment() - ended successfully");
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "Content-Type,Accept,X-Requested-With,authorization")
                .header("Access-Control-Allow-Credentials", true)
                .entity("Your payment was sent to our system and is verified now. You will accept an email soon with payment confirmation. Thanks for choosing Intuit's Payment System.")
                .build();
    }

    @GetMapping(value = "/payment-methods")
    @Produces("application/json")
    @Path("/payment-methods")
    @GET
    public String getAllPaymentMethods() throws URISyntaxException {
        logger.info("getAllPaymentMethods() was called");
        PaymentMethod first = new PaymentMethod(PaymentMethodType.BANK_ACCOUNT, "Chace Checking Account","*1567");
        ArrayList<PaymentMethod> allPaymentMethods = new ArrayList<PaymentMethod>();
        allPaymentMethods.add(first);
        logger.info("getAllPaymentMethods() ended successfully");
        return gson.toJson(allPaymentMethods);
    }

    @GetMapping(value = "/payees")
    @Produces("application/json")
    @Path("/payees")
    @GET
    public String getAllPayees() {
        logger.info("getAllPayees() was called");
        Payee first = new Payee("jonh.smith@gmail.com", "Jonh Smith");
        ArrayList<Payee> allPayees = new ArrayList<Payee>();
        allPayees.add(first);
        logger.info("getAllPayees() ended successfully");
        return gson.toJson(allPayees);
    }

}
