package com.vesinitsyn.watches_catalog.domain.model;

import lombok.Getter;

@Getter
public class NoDiscount implements Discount {
    private static final NoDiscount INSTANCE = new NoDiscount();
    private final DiscountType discountType = DiscountType.NO_DISCOUNT;

    private NoDiscount() {
    }

    public static NoDiscount getInstance() {
        return INSTANCE;
    }
}
