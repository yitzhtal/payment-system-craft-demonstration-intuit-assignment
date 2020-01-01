package integration;

import beans.Payment;
import com.risk.engine.RiskEngineApplication;
import interfaces.DBServiceInf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import services.PaymentTracker;
import services.RiskEngineService;

import java.util.Random;

import static org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiskEngineApplication.class)
public class RiskEngineTest {
    @Autowired
    RiskEngineService riskEngineService;

    @Autowired
    PaymentTracker paymentTracker;

    //We test the logic of 30% of payments rejected, although this is probably only temporary logic for POC...
    @Test
    public void testAnalyze30PercentRejected() throws Exception {
        Random r = new Random();

        for(int i=0; i<10; i++) {
            riskEngineService.analyze(new Payment((long) r.nextInt(1000),"USD","MorBasson","Talitz","123412341234"));
        }

        //for every 10 payments, we reject 3
        Assert.assertTrue(paymentTracker.getRejectedPayments().get() == 3);
    }

    @Test
    public void testAnalyze70PercentApproved() throws Exception {
        Random r = new Random();

        for(int i=0; i<7; i++) {
            riskEngineService.analyze(new Payment((long) r.nextInt(1000),"USD","MorBasson","MahmoodShaib","123412341234"));
        }

        //for every 10 payments, we reject 3
        Assert.assertTrue(paymentTracker.getApprovedPayments().get() == 7);
    }

    @Test
    public void testCalculateRiskScore() throws Exception {
        Payment p = new Payment(new Long(10000000),"USD","MorBasson","Talitz","123412341234");
        int riskScore = riskEngineService.calculateRiskScore(p);
        Assert.assertTrue(riskScore >=0 && riskScore <=10 );
    }

}
