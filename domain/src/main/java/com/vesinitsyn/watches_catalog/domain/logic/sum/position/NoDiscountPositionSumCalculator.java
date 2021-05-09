package com.vesinitsyn.watches_catalog.domain.logic.sum.position;

import com.vesinitsyn.watches_catalog.domain.model.DiscountType;
import com.vesinitsyn.watches_catalog.domain.model.NoDiscount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NoDiscountPositionSumCalculator implements PositionSumCalculator<NoDiscount> {

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.NO_DISCOUNT;
    }

    @Override
    public BigDecimal calculate(BigDecimal price,
                                BigDecimal quantity,
                                NoDiscount discount) {
        return price.multiply(quantity);
    }
}
