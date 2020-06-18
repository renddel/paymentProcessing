package com.webapp.paymentprocessing.model;

public enum Type {
    TYPE1,
    TYPE2,
    TYPE3;

    public String getType() {
        return this.name();
    }
}
