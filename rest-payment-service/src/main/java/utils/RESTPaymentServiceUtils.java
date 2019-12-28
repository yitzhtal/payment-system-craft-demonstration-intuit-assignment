package utils;

import com.paymentservice.LoggingController;
import enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.Response;

@Component
public class RESTPaymentServiceUtils {

    @Autowired
    LoggingController logger;

    public boolean isAllFieldsNotEmpty(Long amount, String currency, String userId, String payeeId, String paymentMethodId) {
        if (StringUtils.isEmpty(amount) || StringUtils.isEmpty(currency) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(payeeId) || StringUtils.isEmpty(paymentMethodId)) {
            return false;
        }
        return true;
    }

    public boolean isPaymentMethodValid(String paymentMethodId) {
        return CreditCardValidation.isValid(Long.valueOf(paymentMethodId));
    }

    public boolean isSupportedCurrency(String currencyToVerify) {
        for (Currency curr : Currency.values()) {
            if (curr.name().equalsIgnoreCase(currencyToVerify)) return true;
        }
        return false;
    }
}
