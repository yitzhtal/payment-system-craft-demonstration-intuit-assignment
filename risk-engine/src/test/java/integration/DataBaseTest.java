package integration;

import com.risk.engine.RiskEngineApplication;
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

import static org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RiskEngineApplication.class)
@Ignore
public class DataBaseTest {
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    RiskEngineService riskEngineService;

    @Autowired
    PaymentTracker paymentTracker;

    @Before
    public void setUp() {
        webAppContextSetup(wac);
    }

    @Test
    @Ignore
    public void testAddRowsToDB() throws Exception {
//        Assert.assertTrue(dbService.getAllTableRows().size() == 0);
//        dbService.createProcessedPaymentsTable();
//        dbService.getJdbcTemplate().batchUpdate("INSERT INTO processed_payments(amount, currency, userId, payeeId, paymentMethodId, riskScore, approved) " +
//                "VALUES ('1000','USD','MorBasson','Talitz','1234123412341234','10','false')");
//        Assert.assertTrue(dbService.getAllTableRows().size() == 1);
//        dbService.getJdbcTemplate().batchUpdate("INSERT INTO processed_payments(amount, currency, userId, payeeId, paymentMethodId, riskScore, approved) " +
//                "VALUES ('1000','USD','MorBasson','Talitz','1234123412341234','10','false')");
//        Assert.assertTrue(dbService.getAllTableRows().size() == 2);
    }

}
