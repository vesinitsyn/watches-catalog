package com.vesinitsyn.domain.logic.sum.position;

import com.vesinitsyn.domain.model.Discount;

import java.math.BigDecimal;

public interface PositionSumCalculator<T extends Discount> {
    BigDecimal calculate(BigDecimal price,
                         BigDecimal quantity,
                         T discount);
}
