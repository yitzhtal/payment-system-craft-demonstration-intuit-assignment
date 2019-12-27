package beans;

public class ProcessedPayment extends Payment {
    private int riskScore;
    private boolean approved;

    public ProcessedPayment(Long amount, String currency, String userId, String payeeId, String paymentMethodId, int riskScore) {
        super(amount, currency, userId, payeeId, paymentMethodId);
        this.riskScore = riskScore;
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
}
