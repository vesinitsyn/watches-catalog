package com.vesinitsyn.domain.logic.sum.position;

import com.vesinitsyn.domain.model.QuantityDiscount;

import java.math.BigDecimal;

public class QuantityDiscountPositionSumCalculator implements PositionSumCalculator<QuantityDiscount> {

    @Override
    public BigDecimal calculate(BigDecimal price,
                                BigDecimal quantity,
                                QuantityDiscount discount) {
        throw new UnsupportedOperationException();
    }
}
