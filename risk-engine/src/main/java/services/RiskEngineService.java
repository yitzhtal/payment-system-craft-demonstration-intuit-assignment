package services;

import beans.Payment;
import beans.ProcessedPayment;
import com.risk.engine.LoggingController;
import interfaces.RiskEngineServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RiskEngineService implements RiskEngineServiceInf {
    @Autowired
    LoggingController logger;

    @Autowired
    DBService dataBaseService;

    public ProcessedPayment analyze(Payment payment) {
        logger.info("analyze() - called");

        logger.info("analyze() - calculating risk score for payment");
        int riskScore = calculateRiskScore(payment);

        ProcessedPayment processedPayment = new ProcessedPayment(payment.getAmount(),payment.getCurrency(),payment.getUserId(),payment.getPayeeId(),payment.getPaymentMethodId(),riskScore);

        logger.info("analyze() - determining if payment should be accepted or not");
        if(isPaymentAccepted(processedPayment)) {
            processedPayment.setApproved(true);
        } else {
            processedPayment.setApproved(false);
        }
        logger.info("analyze() - finished");
        return processedPayment;
    }

    public int calculateRiskScore(Payment payment) {
        return new Random().nextInt(11); //ignore any logic, and returns a random number that represents the risk score
    }

    public boolean isPaymentAccepted(ProcessedPayment processedPayment) {
        if(dataBaseService.getApprovedPayments().get() < 7) { //we can approve more payments
            dataBaseService.getApprovedPayments().incrementAndGet();
            return true;
        } else {
            int rejectedPayments = dataBaseService.getRejectedPayments().incrementAndGet();
            if(rejectedPayments == 3) dataBaseService.getApprovedPayments().set(0); //start approving payments again
            return false;
        }
    }
}
