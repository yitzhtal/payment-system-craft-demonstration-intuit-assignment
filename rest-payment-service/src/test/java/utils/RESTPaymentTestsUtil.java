package utils;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class RESTPaymentTestsUtil {

    public MultiValueMap<String, String> preparePaymentRequest(String paymentMethodId, String userId, String amount, String currency, String payeeId) {
            MultiValueMap<String, String> paymentRequest = new LinkedMultiValueMap<String, String>();

            paymentRequest.add("paymentMethodId", paymentMethodId); //valid credit card number
            paymentRequest.add("userId",  userId);
            paymentRequest.add("amount", amount);
            paymentRequest.add("currency", currency);
            paymentRequest.add("payeeId", payeeId);

            return paymentRequest;
    }
}
