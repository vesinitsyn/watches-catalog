package com.vesinitsyn.domain.logic.sum.position;

import com.vesinitsyn.domain.model.Discount;
import com.vesinitsyn.domain.model.DiscountType;

import java.math.BigDecimal;

public interface PositionSumCalculator<T extends Discount> {
    DiscountType getDiscountType();

    BigDecimal calculate(BigDecimal price,
                         BigDecimal quantity,
                         T discount);
}
