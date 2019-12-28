package beans;

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
                "riskScore=" + riskScore +
                ", approved=" + approved +
                '}';
    }
}
