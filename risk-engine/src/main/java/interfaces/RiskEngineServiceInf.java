package interfaces;

import beans.Payment;
import beans.ProcessedPayment;

public interface RiskEngineServiceInf {
    public ProcessedPayment analyze(Payment payment);
    public int calculateRiskScore(Payment payment);
}
