package com.healinghaven.bigmomma.entity;

public class Sale extends Entity{
    private String date;
    private Fee fees;
    private double totalAmount;
    private Vendor vendor;
    private User buyer;
    @Override
    public String toString() {
        return String.format("Sale{date[%s], fees[%s], totalAmount[%s]}", date, fees, totalAmount);
    }
}
