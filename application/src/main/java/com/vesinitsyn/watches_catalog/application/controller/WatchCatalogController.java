package com.vesinitsyn.watches_catalog.application.controller;

import com.vesinitsyn.watches_catalog.application.controller.api.CheckoutResponse;
import com.vesinitsyn.watches_catalog.domain.logic.sum.basket.BasketSumCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Service
@RestController
@RequiredArgsConstructor
public class WatchCatalogController {

    private final BasketSumCalculationService basketSumCalculationService;

    @PostMapping("/checkout")
    public CheckoutResponse checkout(@RequestBody List<String> purchasedWatchIds) {
        BigDecimal price = basketSumCalculationService.calculate(purchasedWatchIds);
        return new CheckoutResponse(price);
    }
}
