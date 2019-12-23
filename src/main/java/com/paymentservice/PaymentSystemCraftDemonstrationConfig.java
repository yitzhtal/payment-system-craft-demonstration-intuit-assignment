package com.paymentservice;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
 
@Component
public class PaymentSystemCraftDemonstrationConfig extends ResourceConfig
{
    public PaymentSystemCraftDemonstrationConfig()
    {
        register(PaymentService.class);
    }
}
