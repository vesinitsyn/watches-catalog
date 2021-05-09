package com.vesinitsyn.domain.logic.sum.position;

import com.vesinitsyn.domain.model.DiscountType;
import com.vesinitsyn.domain.model.NoDiscount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.vesinitsyn.domain.model.DiscountType.NO_DISCOUNT;

@Service
public class NoDiscountPositionSumCalculator implements PositionSumCalculator<NoDiscount> {

    @Override
    public DiscountType getDiscountType() {
        return NO_DISCOUNT;
    }

    @Override
    public BigDecimal calculate(BigDecimal price,
                                BigDecimal quantity,
                                NoDiscount discount) {
        return price.multiply(quantity);
    }
}
