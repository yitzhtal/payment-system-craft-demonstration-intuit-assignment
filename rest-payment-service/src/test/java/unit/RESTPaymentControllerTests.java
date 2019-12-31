package unit;

import com.google.gson.Gson;
import com.paymentservice.LoggingController;
import com.paymentservice.RESTPaymentController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import services.RabbitMQService;
import utils.RESTPaymentControllerUtils;
import utils.RESTPaymentTestsUtil;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RESTPaymentControllerTests.class})
public class RESTPaymentControllerTests {
    final String basePath = "/payment-service";

    private MockMvc mockMvc;

    private RESTPaymentTestsUtil restPaymentTestsUtil = new RESTPaymentTestsUtil();

    @Mock
    LoggingController logger;

    @InjectMocks
    private RESTPaymentController restPaymentController ;

    private Gson gson = new Gson();

    @Mock
    private RabbitMQService rabbitMQService;

    RESTPaymentControllerUtils restPaymentControllerUtils = new RESTPaymentControllerUtils();


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restPaymentController).build();
        restPaymentController.setRestPaymentControllerUtils(restPaymentControllerUtils); //inject real object to rest controller
        restPaymentController.setGson(gson);//inject real object to rest controller
    }

    @Test
    public void testCreatePaymentOnSuccess() throws Exception {
        //Mocking the RabbitMQ service - we don't want to publish messages to the queue on our tests!
        doNothing().when(rabbitMQService).publishMessage(anyString());

        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=5196081888500645&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson", status().isOk());
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=5196081888500645&userId=Talitz&amount=10000&currency=EUR&payeeId=Nir_Harel", status().isOk());
    }

    @Test
    public void testCreatePaymentOnCreditCardValidationFailure() throws Exception {
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=0000000000000000&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson", status().isBadRequest());
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=12345678890&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson", status().isBadRequest());
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=!#!#!$$!$!#!$&userId=Tal&amount=100&currency=USD&payeeId=Mor_Basson", status().isBadRequest());
    }

    @Test
    public void testCreatePaymentOnEmptyRequestFailure() throws Exception {
        //all fields are empty
        restPaymentTestsUtil.testCreatePaymentPostRequestWithParams(mockMvc,basePath,"paymentMethodId=&userId=&amount=&currency=&payeeId=", status().isBadRequest());

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
