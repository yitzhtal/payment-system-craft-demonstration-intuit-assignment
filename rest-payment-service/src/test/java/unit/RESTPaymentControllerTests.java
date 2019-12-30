package unit;

import com.google.gson.Gson;
import com.paymentservice.RESTPaymentApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import utils.RESTPaymentTestsUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RESTPaymentApplication.class)
@AutoConfigureMockMvc
public class RESTPaymentControllerTests {
    final String basePath = "/payment-service";

    @Autowired
    private Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RESTPaymentTestsUtil restPaymentTestsUtil;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setUp(){
        webAppContextSetup(wac);
    }

    @Test
    public void testCreatePaymentOnSuccess() throws Exception {
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=5196081888500645&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson", status().isOk());
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=5196081888500645&userId=Talitz&amount=10000&currency=EUR&payeeId=Nir_Harel", status().isOk());
    }

    @Test
    public void testCreatePaymentOnCreditCardValidationFailure() throws Exception {
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=0000000000000000&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson", MockMvcResultMatchers.status().isBadRequest());
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=12345678890&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson", MockMvcResultMatchers.status().isBadRequest());
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=!#!#!$$!$!#!$&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson", MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreatePaymentOnEmptyRequestFailure() throws Exception {
        //all fields are empty
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=&userId=&amount=&currency=&payeeId=", MockMvcResultMatchers.status().isBadRequest());

        //user id is empty
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=0000000000000000&userId=&amount=100&currency=USD&payeeId=Mor_Basson", status().isBadRequest());

        //all fields are not sent
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"", status().isBadRequest());
    }

    @Test
    public void testGetAllPaymentMethods() throws Exception {
        this.mockMvc.perform(get(basePath+"/payment-methods")).andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void testGetAllPayees() throws Exception {
        this.mockMvc.perform(get(basePath+"/payees")).andDo(print()).andExpect(status().isOk()).andReturn();
    }
}
