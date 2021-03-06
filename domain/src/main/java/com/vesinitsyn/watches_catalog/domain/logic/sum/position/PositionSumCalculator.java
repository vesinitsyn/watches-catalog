package com.vesinitsyn.watches_catalog.domain.logic.sum.position;

import com.vesinitsyn.watches_catalog.domain.model.Discount;
import com.vesinitsyn.watches_catalog.domain.model.DiscountType;

import java.math.BigDecimal;

public interface PositionSumCalculator<T extends Discount> {
    DiscountType getDiscountType();

    BigDecimal calculate(BigDecimal price,
                         BigDecimal quantity,
                         T discount);
}
