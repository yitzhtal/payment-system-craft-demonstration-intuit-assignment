package com.paymentservice;

import beans.Payee;
import beans.Payment;
import beans.PaymentMethod;
import com.google.gson.Gson;
import enums.PaymentMethodType;
import org.springframework.beans.factory.annotation.Autowired;
import rabbitmq.RabbitMQService;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "payments")
@Path("/payment-service")
public class PaymentService {

    @Autowired
    LoggingController logger;

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    Gson gson;

    @GET
    @Path("/payment-methods")
    @Produces("application/json")
    public ArrayList<PaymentMethod> getAllPaymentMethods() throws URISyntaxException {
        logger.info("getAllPaymentMethods() was called");
        PaymentMethod first = new PaymentMethod(PaymentMethodType.BANK_ACCOUNT, "Chace Checking Account","*1567");
        ArrayList<PaymentMethod> allPaymentMethods = new ArrayList<PaymentMethod>();
        allPaymentMethods.add(first);
        logger.info("getAllPaymentMethods() ended successfully");
        return allPaymentMethods;
    }

    @GET
    @Produces("application/json")
    @Path("/payees")
    public ArrayList<Payee> getAllPayees() {
        logger.info("getAllPayees() was called");
        Payee first = new Payee("jonh.smith@gmail.com", "Jonh Smith");
        ArrayList<Payee> allPayees = new ArrayList<Payee>();
        allPayees.add(first);
        logger.info("getAllPayees() ended successfully");
        return allPayees;
    }
     
    @POST
    @Consumes("application/json")
    @Path("/create-payment")
    public Response createPayment(Payment payment) {
        logger.info("createPayment() - was called");
        if(payment.getAmount() == null || payment.getCurrency() == null || payment.getPayeeId()  == null || payment.getPaymentMethodId() == null || payment.getUserId() == null) {
            return Response.status(400).entity("Please provide all mandatory inputs").build();
        }

        String userId = payment.getUserId();
        String payeeId = payment.getPayeeId();
        String paymentMethodId = payment.getPaymentMethodId();

        payment.setUserId(UUID.fromString(userId).toString());
        payment.setPayeeId(UUID.fromString(payeeId).toString());
        payment.setPaymentMethodId(UUID.fromString(paymentMethodId).toString());

        try {
            rabbitMQService.publishMessage(gson.toJson(payment));
        } catch(Exception e) {
            logger.info("createPayment() -  failed in connecting to rabbitmq");
            return Response.status(500).build();
        }

        logger.info("createPayment() - ended successfully");
        return Response.status(201).build();
    }

}
