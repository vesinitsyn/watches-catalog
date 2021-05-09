package com.vesinitsyn.domain.logic.sum.basket;

import com.vesinitsyn.domain.model.Basket;
import com.vesinitsyn.infrastructure.WatchesStorage;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CreateBasketService {

    private final WatchesStorage watchesStorage;

    public Basket createBasket(List<String> purchasedWatchIds) {
        throw new UnsupportedOperationException();
    }
}
