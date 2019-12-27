package com.paymentservice;

import beans.Payee;
import beans.Payment;
import beans.PaymentMethod;
import com.google.gson.Gson;
import enums.PaymentMethodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import rabbitmq.RabbitMQService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

@CrossOrigin("http://localhost:8080")
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "payments")
@Path("/payment-service")
public class RESTPaymentService {

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
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/create-payment")
    public Response createPayment(@FormParam("amount") long amount, @FormParam("currency") String currency, @FormParam("userId") String userId,
                                  @FormParam("payeeId") String payeeId, @FormParam("paymentMethodId") String paymentMethodId) {
        logger.info("createPayment() - was called");
        if(amount < 0 || currency == null || userId  == null || payeeId == null || paymentMethodId == null) {
            return Response.status(400).entity("Please provide all mandatory inputs").build();
        }
        Payment payment = new Payment(amount,currency, userId, payeeId,  paymentMethodId);

        //check this GUID
        payment.setUserId(UUID.randomUUID().toString());
        payment.setPayeeId(UUID.randomUUID().toString());
        payment.setPaymentMethodId(UUID.randomUUID().toString());

        try {
            rabbitMQService.publishMessage(gson.toJson(payment));
        } catch(Exception e) {
            logger.info("createPayment() -  failed in connecting to rabbitmq");
            return Response.status(500).build();
        }

        logger.info("createPayment() - ended successfully");
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .header("Access-Control-Allow-Headers", "Content-Type,Accept,X-Requested-With,authorization")
                .header("Access-Control-Allow-Credentials", true)
                .build();
    }

}
