package services;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PaymentTracker {

    private AtomicInteger approvedPayments = new AtomicInteger(0);
    private AtomicInteger rejectedPayments = new AtomicInteger(0);


    public AtomicInteger getApprovedPayments() {
        return approvedPayments;
    }

    public void setApprovedPayments(AtomicInteger approvedPayments) {

        this.approvedPayments = approvedPayments;
    }

    public AtomicInteger getRejectedPayments() {

        return rejectedPayments;
    }

    public void setRejectedPayments(AtomicInteger rejectedPayments) {

        this.rejectedPayments = rejectedPayments;
    }
}
