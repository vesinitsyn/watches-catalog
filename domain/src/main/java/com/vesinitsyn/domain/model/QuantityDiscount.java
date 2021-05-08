package com.vesinitsyn.domain.model;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

import static com.vesinitsyn.domain.model.DiscountType.QUANTITY;

@Getter
@Accessors(chain = true)
public class QuantityDiscount implements Discount {
    private final DiscountType discountType = QUANTITY;
    private final BigDecimal quantity;
    private final BigDecimal price;

    public QuantityDiscount(BigDecimal quantity, BigDecimal price) {
        this.quantity = quantity;
        this.price = price;
    }
}
