package com.vesinitsyn.watches_catalog.domain.logic.sum.basket;

import com.vesinitsyn.watches_catalog.domain.logic.sum.basket.validation.WatchesValidationService;
import com.vesinitsyn.watches_catalog.domain.model.Basket;
import com.vesinitsyn.watches_catalog.domain.model.Watch;
import com.vesinitsyn.watches_catalog.infrastructure.WatchesStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.vesinitsyn.watches_catalog.domain.model.Basket.emptyBasket;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class CreateBasketService {

    private final WatchesStorage watchesStorage;
    private final WatchesValidationService watchesValidationService;

    public Basket createBasket(List<String> purchasedWatchIds) {
        if (purchasedWatchIds == null || purchasedWatchIds.isEmpty()) {
            return emptyBasket();
        }
        Map<String, Long> quantityByWatchId = purchasedWatchIds.stream()
                .collect(groupingBy(identity(), counting()));
        Set<String> uniqueWatchIds = quantityByWatchId.keySet();
        Set<Watch> watches = watchesStorage.findByIds(uniqueWatchIds);
        watchesValidationService.validate(watches, uniqueWatchIds);
        Map<Watch, BigDecimal> quantityByWatch = watches.stream()
                .collect(toMap(
                        identity(),
                        watch -> BigDecimal.valueOf(quantityByWatchId.get(watch.getId()))
                        )
                );
        return new Basket(quantityByWatch);
    }
}
