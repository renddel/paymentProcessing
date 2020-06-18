package com.webapp.paymentprocessing.search;

public class PaymentSearch {
    private boolean active;
    private Long amount;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
