package beans;

import java.io.Serializable;
import java.util.Objects;

public class Payment implements Serializable {
  
    private static final long serialVersionUID = 1L;

    private String amount;

    private String currency;

    private String userId;

    private String payeeId;

    private String paymentMethodId;

    public Payment(String amount, String currency, String userId, String payeeId, String paymentMethodId) {
        this.amount = amount;
        this.currency = currency;
        this.userId = userId;
        this.payeeId = payeeId;
        this.paymentMethodId = paymentMethodId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(amount, payment.amount) &&
                Objects.equals(currency, payment.currency) &&
                Objects.equals(userId, payment.userId) &&
                Objects.equals(payeeId, payment.payeeId) &&
                Objects.equals(paymentMethodId, payment.paymentMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency, userId, payeeId, paymentMethodId);
    }
}
