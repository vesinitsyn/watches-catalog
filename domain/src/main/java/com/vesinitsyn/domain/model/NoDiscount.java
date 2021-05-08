package com.vesinitsyn.domain.model;

import lombok.Getter;

import static com.vesinitsyn.domain.model.DiscountType.NO_DISCOUNT;

@Getter
public class NoDiscount implements Discount {
    private final DiscountType discountType = NO_DISCOUNT;
}
