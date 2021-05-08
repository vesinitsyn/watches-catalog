package com.vesinitsyn.domain.logic.sum.position;

import com.vesinitsyn.domain.model.Discount;
import com.vesinitsyn.domain.model.DiscountType;
import com.vesinitsyn.domain.model.Watch;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@SuppressWarnings({"unchecked", "rawtypes"})
public class DiscountAccountingPositionSumCalculator {

    private final Map<DiscountType, PositionSumCalculator> calculators;

    public DiscountAccountingPositionSumCalculator(List<PositionSumCalculator> positionSumCalculators) {
        calculators = positionSumCalculators.stream()
                .collect(toMap(PositionSumCalculator::getDiscountType, Function.identity()));
    }

    public BigDecimal calculate(Watch watch,
                                BigDecimal quantity,
                                Discount discount) {
        DiscountType discountType = discount.getDiscountType();
        return calculators.get(discountType).calculate(
                watch.getPrice(),
                quantity,
                discount
        );
    }
}
