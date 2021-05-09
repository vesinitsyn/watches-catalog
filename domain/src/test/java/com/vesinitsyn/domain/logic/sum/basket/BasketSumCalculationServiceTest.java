package com.vesinitsyn.domain.logic.sum.basket;

import com.vesinitsyn.domain.logic.sum.position.DiscountAccountingPositionSumCalculator;
import com.vesinitsyn.domain.model.Basket;
import com.vesinitsyn.domain.model.NoDiscount;
import com.vesinitsyn.domain.model.QuantityDiscount;
import com.vesinitsyn.domain.model.Watch;
import com.vesinitsyn.infrastructure.DiscountsStorage;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.vesinitsyn.domain.model.Basket.emptyBasket;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketSumCalculationServiceTest {

    @Mock
    private CreateBasketService createBasketService;
    @Mock
    private DiscountsStorage discountsStorage;
    @Mock
    private DiscountAccountingPositionSumCalculator discountAccountingPositionSumCalculator;

    @InjectMocks
    private BasketSumCalculationService basketSumCalculationService;

    @Test
    void calculate_WhenBasketIsEmpty_ShouldReturnZeroSum() {
        // Given
        List<String> purchasedWatchIds = List.of();

        // mock CreateBasketService
        when(createBasketService.createBasket(purchasedWatchIds))
                .thenReturn(emptyBasket());

        BigDecimal expectedSum = ZERO;

        // When
        BigDecimal actualSum = basketSumCalculationService.calculate(purchasedWatchIds);

        // Then
        assertEquals(expectedSum, actualSum);
        verifyNoInteractions(discountsStorage, discountAccountingPositionSumCalculator);
    }

    @Test
    void calculate_WhenBasketIsNotEmpty_ShouldCalculateDiscountablePositionWithExpectedDiscountAndNotDiscountableWithNoDiscount() {
        // Given
        String watchId1 = "001";
        String watchId2 = "003";
        List<String> purchasedWatchIds = List.of(watchId1, watchId1, watchId2);
        Watch watch1 = new Watch(watchId1, "Rolex", BigDecimal.valueOf(100D));
        Watch watch2 = new Watch(watchId2, "Swatch", BigDecimal.valueOf(50D));

        // mock CreateBasketService
        BigDecimal watch1Quantity = BigDecimal.valueOf(2L);
        BigDecimal watch2Quantity = BigDecimal.ONE;
        when(createBasketService.createBasket(purchasedWatchIds))
                .thenReturn(new Basket(
                        Map.of(
                                watch1, watch1Quantity,
                                watch2, watch2Quantity
                        )
                ));

        // mock DiscountsStorage
        QuantityDiscount watch1Discount = new QuantityDiscount(
                BigDecimal.valueOf(3L),
                BigDecimal.valueOf(200D)
        );
        when(discountsStorage.findByWatchIds(Set.of(watchId1, watchId2)))
                .thenReturn(Map.of(
                        watchId1, watch1Discount
                ));

        // mock DiscountAccountingPositionSumCalculator
        when(discountAccountingPositionSumCalculator.calculate(
                watch1, watch1Quantity, watch1Discount
        )).thenReturn(BigDecimal.valueOf(200D));
        when(discountAccountingPositionSumCalculator.calculate(
                watch2, watch2Quantity, NoDiscount.getInstance()
        )).thenReturn(BigDecimal.valueOf(50D));

        BigDecimal expectedSum = BigDecimal.valueOf(250D);

        // When
        BigDecimal actualSum = basketSumCalculationService.calculate(purchasedWatchIds);

        // Then
        assertThat(actualSum)
                .usingComparator(BigDecimalComparator.BIG_DECIMAL_COMPARATOR)
                .isEqualTo(expectedSum);
        verifyNoMoreInteractions(
                discountsStorage,
                discountAccountingPositionSumCalculator
        );
    }
}