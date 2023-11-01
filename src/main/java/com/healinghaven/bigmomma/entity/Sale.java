package com.healinghaven.bigmomma.entity;

import java.util.Date;

public class Sale {
    private String Id;
    private String date;
    private Fee fees;
    private double totalAmount;

    @Override
    public String toString() {
        return String.format("Sale{date[%s], fees[%s], totalAmount[%s]}", date, fees, totalAmount);
    }
}
