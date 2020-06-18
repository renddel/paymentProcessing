package com.webapp.paymentprocessing.model;

public enum Currency {
    EUR,
    USD;

    public String getCurrency() {
        return this.name();
    }
}
