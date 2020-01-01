package beans;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class ProcessedPayment extends Payment {
    private int riskScore = -1;
    private boolean approved = false;

    public ProcessedPayment(Long amount, String currency, String userId, String payeeId, String paymentMethodId, int riskScore, boolean approved) {
        super(amount, currency, userId, payeeId, paymentMethodId);
        this.riskScore = riskScore;
        this.approved = approved;
    }

    public ProcessedPayment(Long amount, String currency, String userId, String payeeId, String paymentMethodId, int riskScore) {
        super(amount, currency, userId, payeeId, paymentMethodId);
        this.riskScore = riskScore;
    }

    public ProcessedPayment() {
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "ProcessedPayment{" +
                "amount='" + super.getAmount() +"'"+
                ", currency='" + super.getCurrency() + '\'' +
                ", userId='" + super.getUserId() + '\'' +
                ", payeeId='" + super.getPayeeId() + '\'' +
                ", paymentMethodId='" + super.getPaymentMethodId() + '\'' +
                ", riskScore='" + riskScore + "'"+
                ", approved='" + approved +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProcessedPayment that = (ProcessedPayment) o;
        return riskScore == that.riskScore &&
                approved == that.approved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), riskScore, approved);
    }
}
