package com.vesinitsyn.watches_catalog.domain.logic.sum.basket;

import com.vesinitsyn.watches_catalog.domain.logic.sum.position.DiscountAccountingPositionSumCalculator;
import com.vesinitsyn.watches_catalog.domain.model.Basket;
import com.vesinitsyn.watches_catalog.domain.model.Discount;
import com.vesinitsyn.watches_catalog.domain.model.NoDiscount;
import com.vesinitsyn.watches_catalog.domain.model.Watch;
import com.vesinitsyn.watches_catalog.infrastructure.DiscountsStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class BasketSumCalculationService {

    private final CreateBasketService createBasketService;
    private final DiscountsStorage discountsStorage;
    private final DiscountAccountingPositionSumCalculator discountAccountingPositionSumCalculator;

    public BigDecimal calculate(List<String> purchasedWatchIds) {
        Basket basket = createBasketService.createBasket(purchasedWatchIds);
        Map<Watch, BigDecimal> quantityByWatch = basket.getQuantityByWatch();
        if (quantityByWatch.isEmpty()) {
            return ZERO;
        }
        Map<String, Discount> discountByWatchId = findDiscounts(quantityByWatch);
        return calculateBasketSum(quantityByWatch, discountByWatchId);
    }

    private Map<String, Discount> findDiscounts(Map<Watch, BigDecimal> quantityByWatch) {
        Set<String> watchIds = quantityByWatch.keySet().stream()
                .map(Watch::getId)
                .collect(toSet());
        return discountsStorage.findByWatchIds(watchIds);
    }

    private BigDecimal calculateBasketSum(Map<Watch, BigDecimal> quantityByWatch,
                                          Map<String, Discount> discountByWatchId) {
        return quantityByWatch.entrySet().stream()
                .map(entry -> calculatePositionSum(entry, discountByWatchId))
                .reduce(BigDecimal::add)
                .orElse(ZERO);
    }

    private BigDecimal calculatePositionSum(Map.Entry<Watch, BigDecimal> entry,
                                            Map<String, Discount> discountByWatchId) {
        Watch watch = entry.getKey();
        BigDecimal quantity = entry.getValue();
        Discount discount = discountByWatchId.getOrDefault(watch.getId(), NoDiscount.getInstance());
        return discountAccountingPositionSumCalculator.calculate(
                watch,
                quantity,
                discount
        );
    }
}
