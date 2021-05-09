package com.vesinitsyn.domain.model;

import lombok.Getter;

import static com.vesinitsyn.domain.model.DiscountType.NO_DISCOUNT;

@Getter
public class NoDiscount implements Discount {
    private static final NoDiscount INSTANCE = new NoDiscount();
    private final DiscountType discountType = NO_DISCOUNT;

    private NoDiscount() {
    }

    public static NoDiscount getInstance() {
        return INSTANCE;
    }
}
