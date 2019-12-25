package com.paymentservice;

import beans.Payee;
import beans.Payment;
import beans.PaymentMethod;
import com.rabbitmq.client.ConnectionFactory;
import enums.PaymentMethodType;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "payments")
@Path("/payment-service")
public class PaymentService {
    private static Map<Integer, Payment> DB = new HashMap<>();

    @Autowired
    private LoggingController logger;

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

        ConnectionFactory factory = new ConnectionFactory();
        //docker run -d --hostname rab-con --name rab-con -p 8081:15672 -e RABBITMQ_DEFAULT_USER=sbseg -e RABBITMQ_DEFAULT_PASS=ftw -e RABBITMQ_DEFAULT_VHOST=my_vhost rabbitmq:3-management
        try {
            factory.setUsername("sbseg");
            factory.setPassword("ftw");
            factory.setVirtualHost("my_vhost");
            factory.setHost("localhost");
            factory.setPort(8081);
            logger.info("createPayment() -  create new connection");
            Connection connection = factory.newConnection();
            logger.info("createPayment() -  create channel");
            Channel channel = connection.createChannel();
            channel.queueDeclare("payments-queue", false, false, false, null);
            logger.info("createPayment() -  declaring queue");
            String message = "Hello World!";
            logger.info("createPayment() -  publishing message");
            channel.basicPublish("", "payments-queue", null, message.getBytes());
            logger.info("createPayment() -  message sent to queue: " + message + ".");
        } catch(Exception e) {
            logger.info("createPayment() -  failed in connecting to rabbitmq");
            return Response.status(500).build();
        }

        logger.info("createPayment() - ended successfully");
        return Response.status(201).build();
    }
}
