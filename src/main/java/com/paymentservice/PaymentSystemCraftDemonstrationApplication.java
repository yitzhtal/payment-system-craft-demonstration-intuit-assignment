package com.paymentservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
 
@SpringBootApplication
public class PaymentSystemCraftDemonstrationApplication extends SpringBootServletInitializer
{
    public static void main(String[] args) 
    {
        new PaymentSystemCraftDemonstrationApplication().configure(new SpringApplicationBuilder(PaymentSystemCraftDemonstrationApplication.class)).run(args);
    }
    
    
}
