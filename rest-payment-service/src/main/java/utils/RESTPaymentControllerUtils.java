package utils;

import com.paymentservice.LoggingController;
import enums.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RESTPaymentControllerUtils {

    @Autowired
    LoggingController logger;

    public boolean isAllFieldsNotEmpty(String amount, String currency, String userId, String payeeId, String paymentMethodId) {
        if (StringUtils.isEmpty(amount) || StringUtils.isEmpty(currency) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(payeeId) || StringUtils.isEmpty(paymentMethodId)) {
            return false;
        }
        return true;
    }

    public boolean isPaymentMethodValid(String paymentMethodId) {
        return isStringContainsOnlyNumbers(paymentMethodId) && CreditCardValidation.isValid(paymentMethodId);
    }

    public boolean isSupportedCurrency(String currencyToVerify) {
        for (Currency curr : Currency.values()) {
            if (curr.name().equalsIgnoreCase(currencyToVerify)) return true;
        }
        return false;
    }

    public boolean isStringContainsOnlyNumbers(String str) {
        return str.matches("[0-9]+");
    }

    public boolean isBetweenRange(Long lowerBound, Long upperBound, String amountToCheck) {
        try {
            Long amounToCheckAsLong = Long.parseLong(amountToCheck);
            return amounToCheckAsLong >= lowerBound && amounToCheckAsLong <= upperBound;
        } catch (Exception e) {
            return false;
        }
    }
}
