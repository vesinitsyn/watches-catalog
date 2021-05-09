package com.vesinitsyn.domain.logic.sum.basket;

import com.vesinitsyn.domain.model.Basket;
import com.vesinitsyn.domain.model.Watch;
import com.vesinitsyn.infrastructure.WatchesStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.vesinitsyn.domain.model.Basket.emptyBasket;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBasketServiceTest {

    @Mock
    private WatchesStorage watchesStorage;

    @InjectMocks
    private CreateBasketService createBasketService;

    @Test
    void createBasket_WhenNullPassed_ShouldReturnEmptyBasket() {
        // Given
        List<String> purchasedWatchIds = null;
        Basket expectedBasket = emptyBasket();

        // When
        Basket actualBasket = createBasketService.createBasket(purchasedWatchIds);

        // Then
        assertThat(actualBasket)
                .usingRecursiveComparison()
                .isEqualTo(expectedBasket);
        verifyNoInteractions(watchesStorage);
    }

    @Test
    void createBasket_WhenEmptyListPassed_ShouldReturnEmptyBasket() {
        // Given
        List<String> purchasedWatchIds = List.of();
        Basket expectedBasket = emptyBasket();

        // When
        Basket actualBasket = createBasketService.createBasket(purchasedWatchIds);

        // Then
        assertThat(actualBasket)
                .usingRecursiveComparison()
                .isEqualTo(expectedBasket);
        verifyNoInteractions(watchesStorage);
    }

    @Test
    void createBasket_WhenSingleWatchIdPassed_ShouldReturnExpectedBasket() {
        // Given
        String watchId = "001";
        List<String> purchasedWatchIds = List.of(watchId);

        // mock WatchesStorage
        Watch watch = new Watch(watchId, "Rolex", BigDecimal.valueOf(100D));
        when(watchesStorage.findByIds(new HashSet<>(purchasedWatchIds)))
                .thenReturn(Set.of(watch));

        Basket expectedBasket = new Basket(Map.of(watch, BigDecimal.ONE));

        // When
        Basket actualBasket = createBasketService.createBasket(purchasedWatchIds);

        // Then
        assertThat(actualBasket)
                .usingRecursiveComparison()
                .isEqualTo(expectedBasket);
        verifyNoMoreInteractions(watchesStorage);
    }

    @Test
    void createBasket_WhenSingleWatchIdPassedMultipleTimes_ShouldReturnExpectedBasket() {
        // Given
        String watchId = "001";
        List<String> purchasedWatchIds = List.of(watchId, watchId, watchId);

        // mock WatchesStorage
        Watch watch = new Watch(watchId, "Rolex", BigDecimal.valueOf(100D));
        when(watchesStorage.findByIds(new HashSet<>(purchasedWatchIds)))
                .thenReturn(Set.of(watch));

        Basket expectedBasket = new Basket(Map.of(watch, BigDecimal.valueOf(3L)));

        // When
        Basket actualBasket = createBasketService.createBasket(purchasedWatchIds);

        // Then
        assertThat(actualBasket)
                .usingRecursiveComparison()
                .isEqualTo(expectedBasket);
        verifyNoMoreInteractions(watchesStorage);
    }

    @Test
    void createBasket_WhenDifferentWatchIdPassedMultipleTimes_ShouldReturnExpectedBasket() {
        // Given
        String watchId1 = "001";
        String watchId2 = "002";
        String watchId3 = "003";
        String watchId4 = "004";
        List<String> purchasedWatchIds = List.of(
                watchId1,
                watchId2,
                watchId1,
                watchId4,
                watchId4,
                watchId4,
                watchId3,
                watchId2
        );

        // mock WatchesStorage
        Watch watch1 = new Watch(watchId1, "Rolex", BigDecimal.valueOf(100D));
        Watch watch2 = new Watch(watchId2, "Michael Kors", BigDecimal.valueOf(80D));
        Watch watch3 = new Watch(watchId3, "Swatch", BigDecimal.valueOf(50D));
        Watch watch4 = new Watch(watchId4, "Casio", BigDecimal.valueOf(30D));
        when(watchesStorage.findByIds(new HashSet<>(purchasedWatchIds)))
                .thenReturn(Set.of(watch1, watch2, watch3, watch4));

        Basket expectedBasket = new Basket(
                Map.of(
                        watch1, BigDecimal.valueOf(2L),
                        watch2, BigDecimal.valueOf(2L),
                        watch3, BigDecimal.valueOf(1L),
                        watch4, BigDecimal.valueOf(3L)
                )
        );

        // When
        Basket actualBasket = createBasketService.createBasket(purchasedWatchIds);

        // Then
        assertThat(actualBasket)
                .usingRecursiveComparison()
                .isEqualTo(expectedBasket);
        verifyNoMoreInteractions(watchesStorage);
    }
}