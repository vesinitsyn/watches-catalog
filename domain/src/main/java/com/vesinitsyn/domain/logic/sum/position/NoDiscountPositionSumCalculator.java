package com.vesinitsyn.domain.logic.sum.position;

import com.vesinitsyn.domain.model.NoDiscount;

import java.math.BigDecimal;

public class NoDiscountPositionSumCalculator implements PositionSumCalculator<NoDiscount> {

    @Override
    public BigDecimal calculate(BigDecimal price,
                                BigDecimal quantity,
                                NoDiscount discount) {
        return price.multiply(quantity);
    }
}
