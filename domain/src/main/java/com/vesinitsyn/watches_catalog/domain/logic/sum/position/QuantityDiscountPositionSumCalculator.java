package com.vesinitsyn.watches_catalog.domain.logic.sum.position;

import com.vesinitsyn.watches_catalog.domain.model.DiscountType;
import com.vesinitsyn.watches_catalog.domain.model.QuantityDiscount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class QuantityDiscountPositionSumCalculator implements PositionSumCalculator<QuantityDiscount> {

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.QUANTITY;
    }

    @Override
    public BigDecimal calculate(BigDecimal price,
                                BigDecimal quantity,
                                QuantityDiscount discount) {
        BigDecimal discountPrice = discount.getPrice();
        BigDecimal discountQuantity = discount.getQuantity();
        BigDecimal discountsNumber = quantity.divideToIntegralValue(discountQuantity);

        BigDecimal discountedWatchesSum = discountsNumber.multiply(discountPrice);

        BigDecimal fullPriceQuantity = calculateFullPriceWatchesQuantity(
                quantity,
                discountQuantity,
                discountsNumber
        );
        BigDecimal fullPriceWatchesSum = fullPriceQuantity.multiply(price);

        return fullPriceWatchesSum.add(discountedWatchesSum);
    }

    private BigDecimal calculateFullPriceWatchesQuantity(BigDecimal totalWatchesQuantity,
                                                         BigDecimal discountQuantity,
                                                         BigDecimal discountsNumber) {
        BigDecimal discountedWatchesQuantity = discountQuantity.multiply(discountsNumber);
        return totalWatchesQuantity.subtract(discountedWatchesQuantity);
    }
}
