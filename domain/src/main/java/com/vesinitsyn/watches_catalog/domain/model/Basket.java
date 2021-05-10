package com.vesinitsyn.watches_catalog.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
@RequiredArgsConstructor
public class Basket {
    private final Map<Watch, BigDecimal> quantityByWatch;

    public static Basket emptyBasket() {
        return new Basket(emptyMap());
    }
}
