package com.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication(scanBasePackages={"com.paymentservice", "services","utils"})
public class RESTPaymentApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RESTPaymentApplication.class,args);
    }
}
