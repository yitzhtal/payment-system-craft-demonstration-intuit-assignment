package com.paymentservice;

import beans.Payee;
import beans.Payment;
import beans.PaymentMethod;
import enums.PaymentMethodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
 
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "payments")
@Path("/paymentservice")
public class PaymentService {
    private static Map<Integer, Payment> DB = new HashMap<>();

    @Autowired
    private LoggingController logger;

    @GET
    @Path("/getAllPaymentMethods")
    @Produces("application/json")
    public ArrayList<PaymentMethod> getAllPaymentMethods() throws URISyntaxException {
        logger.info("getAllPaymentMethods() was called");
        PaymentMethod first = new PaymentMethod(PaymentMethodType.BANK_ACCOUNT, "Chace Checking Account","*1567");
        ArrayList<PaymentMethod> allPaymentMethods = new ArrayList<PaymentMethod>();
        allPaymentMethods.add(first);
        return allPaymentMethods;
    }

    @GET
    @Produces("application/json")
    @Path("/getAllPayees")
    public ArrayList<Payee> getAllPayees() {
        logger.info("getAllPayees() was called");
        Payee first = new Payee("jonh.smith@gmail.com", "Jonh Smith");
        ArrayList<Payee> allPayees = new ArrayList<Payee>();
        allPayees.add(first);
        return allPayees;
    }
     
    @POST
    @Consumes("application/json")
    @Path("/createPayment")
    public Response createPayment(Payment payment) {
        logger.info("createPayment() was called");
        if(payment.getAmount() == null || payment.getCurrency() == null || payment.getPayeeId()  == null || payment.getPaymentMethodId() == null || payment.getUserId() == null) {
            return Response.status(400).entity("Please provide all mandatory inputs").build();
        }
        //publishPaymentsToQueue()
        return Response.status(201).build();
    }
}
