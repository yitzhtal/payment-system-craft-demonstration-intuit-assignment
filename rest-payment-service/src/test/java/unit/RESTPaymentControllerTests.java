package unit;

import beans.Payee;
import beans.PaymentMethod;
import com.google.gson.Gson;
import com.paymentservice.RESTPaymentApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utils.RESTPaymentTestsUtil;

import java.util.ArrayList;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RESTPaymentApplication.class)
@AutoConfigureMockMvc
public class RESTPaymentControllerTests {

    final String basePath = "/payment-service";

    @Autowired
    private Gson gson;

    @Autowired
    private RESTPaymentTestsUtil restPaymentTestsUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreatePaymentOnSuccess() throws Exception {
        this.mockMvc.perform(post(basePath+"/create-payment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("paymentMethodId=5196081888500645&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @Test
    @Ignore
    public void testCreatePaymentOnCreditCardValidationFailure() throws Exception {
        this.mockMvc.perform(post(basePath+"/create-payment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("paymentMethodId=0000000000000000&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson"))
                .andDo(print()).andExpect(status().isBadRequest()).andReturn();
    }


    @Test
    public void testGetAllPaymentMethods() throws Exception {
        MvcResult result = this.mockMvc.perform(get(basePath+"/payment-methods")).andDo(print()).andExpect(status().isOk()).andReturn();
        ArrayList<PaymentMethod> allPaymentMethods = gson.fromJson(result.getResponse().getContentAsString(),ArrayList.class);
        Assert.assertTrue(allPaymentMethods.size() > 0);
    }

    @Test
    public void testGetAllPayees() throws Exception {
        MvcResult result = this.mockMvc.perform(get(basePath+"/payees")).andDo(print()).andExpect(status().isOk()).andReturn();
        ArrayList<Payee> allPayees = gson.fromJson(result.getResponse().getContentAsString(),ArrayList.class);
        Assert.assertTrue(allPayees.size() > 0);
    }
}
