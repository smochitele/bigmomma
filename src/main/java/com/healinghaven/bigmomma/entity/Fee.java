package com.healinghaven.bigmomma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fee extends Entity{
    private double serviceFee;
    private double deliveryFee;

    @Override
    public String toString() {
        return String.format("Fee{serviceFee[%s], deliveryFee[%s]}", serviceFee, deliveryFee);
    }
}
