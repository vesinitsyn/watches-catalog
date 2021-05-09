package com.vesinitsyn.domain.logic.sum.basket;

import com.vesinitsyn.domain.model.Basket;
import com.vesinitsyn.domain.model.Watch;
import com.vesinitsyn.infrastructure.WatchesStorage;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.vesinitsyn.domain.model.Basket.emptyBasket;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public class CreateBasketService {

    private final WatchesStorage watchesStorage;

    public Basket createBasket(List<String> purchasedWatchIds) {
        if (purchasedWatchIds == null || purchasedWatchIds.isEmpty()) {
            return emptyBasket();
        }
        Map<String, Long> quantityByWatchId = purchasedWatchIds.stream()
                .collect(groupingBy(identity(), counting()));
        Set<String> uniqueWatchIds = quantityByWatchId.keySet();
        Set<Watch> watches = watchesStorage.findByIds(uniqueWatchIds);
        Map<Watch, BigDecimal> quantityByWatch = watches.stream()
                .collect(toMap(
                        identity(),
                        watch -> BigDecimal.valueOf(quantityByWatchId.get(watch.getId()))
                        )
                );
        return new Basket(quantityByWatch);
    }
}
