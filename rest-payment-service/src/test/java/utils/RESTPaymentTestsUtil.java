package utils;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
public class RESTPaymentTestsUtil {
    public void testCreatePaymentPostRequestWithParams(MockMvc mockMvc, String basePath, String s, ResultMatcher ok) throws Exception {
        mockMvc.perform(post(basePath + "/create-payment")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(s))
                .andDo(print()).andExpect(ok).andReturn();
    }
}
