package com.paymentservice;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Component
@EnableWebMvc
public class Config extends ResourceConfig
{
    public Config()
    {
        register(RESTPaymentController.class);
    }
}
