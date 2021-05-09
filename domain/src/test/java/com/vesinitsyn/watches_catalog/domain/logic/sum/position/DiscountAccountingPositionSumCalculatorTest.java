package com.vesinitsyn.watches_catalog.domain.logic.sum.position;

import com.vesinitsyn.watches_catalog.domain.model.NoDiscount;
import com.vesinitsyn.watches_catalog.domain.model.QuantityDiscount;
import com.vesinitsyn.watches_catalog.domain.model.Watch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.vesinitsyn.watches_catalog.domain.model.DiscountType.NO_DISCOUNT;
import static com.vesinitsyn.watches_catalog.domain.model.DiscountType.QUANTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.BigDecimalComparator.BIG_DECIMAL_COMPARATOR;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiscountAccountingPositionSumCalculatorTest {

    @Mock
    private NoDiscountPositionSumCalculator noDiscountPositionSumCalculator;

    @Mock
    private QuantityDiscountPositionSumCalculator quantityDiscountPositionSumCalculator;

    @BeforeEach
    public void beforeEach() {
        when(noDiscountPositionSumCalculator.getDiscountType()).thenReturn(NO_DISCOUNT);
        when(quantityDiscountPositionSumCalculator.getDiscountType()).thenReturn(QUANTITY);
    }

    @Test
    void calculate_WhenQuantityDiscountWatchPassed_ShouldCalculatePositionSumWithQuantityDiscountCalculator() {
        // Given
        BigDecimal price = BigDecimal.valueOf(100D);
        Watch watch = new Watch("001", "Rolex", price);
        BigDecimal quantity = BigDecimal.valueOf(3D);
        QuantityDiscount discount = new QuantityDiscount(BigDecimal.valueOf(3D), BigDecimal.valueOf(200D));

        BigDecimal expectedPositionSum = BigDecimal.valueOf(200D);

        // mock QuantityDiscountPositionSumCalculator
        when(quantityDiscountPositionSumCalculator.calculate(
                price,
                quantity,
                discount
        )).thenReturn(expectedPositionSum);

        // When
        DiscountAccountingPositionSumCalculator discountAccountingPositionSumCalculator = new DiscountAccountingPositionSumCalculator(
                List.of(
                        noDiscountPositionSumCalculator,
                        quantityDiscountPositionSumCalculator
                )
        );
        BigDecimal actualPositionSum = discountAccountingPositionSumCalculator.calculate(watch, quantity, discount);

        // Then
        assertThat(actualPositionSum)
                .usingComparator(BIG_DECIMAL_COMPARATOR)
                .isEqualTo(expectedPositionSum);
        verify(quantityDiscountPositionSumCalculator).getDiscountType();
        verify(noDiscountPositionSumCalculator).getDiscountType();
        verifyNoMoreInteractions(quantityDiscountPositionSumCalculator, noDiscountPositionSumCalculator);
    }

    @Test
    void calculate_WhenNoDiscountWatchPassed_ShouldCalculatePositionSumWithNoDiscountCalculator() {
        // Given
        BigDecimal price = BigDecimal.valueOf(50D);
        Watch watch = new Watch("003", "Swatch", price);
        BigDecimal quantity = BigDecimal.valueOf(3D);
        NoDiscount discount = NoDiscount.getInstance();

        BigDecimal expectedPositionSum = BigDecimal.valueOf(150D);

        // mock NoDiscountPositionSumCalculator
        when(noDiscountPositionSumCalculator.calculate(
                price,
                quantity,
                discount
        )).thenReturn(expectedPositionSum);

        // When
        DiscountAccountingPositionSumCalculator discountAccountingPositionSumCalculator = new DiscountAccountingPositionSumCalculator(
                List.of(
                        noDiscountPositionSumCalculator,
                        quantityDiscountPositionSumCalculator
                )
        );
        BigDecimal actualPositionSum = discountAccountingPositionSumCalculator.calculate(watch, quantity, discount);

        // Then
        assertThat(actualPositionSum)
                .usingComparator(BIG_DECIMAL_COMPARATOR)
                .isEqualTo(expectedPositionSum);
        verify(quantityDiscountPositionSumCalculator).getDiscountType();
        verify(noDiscountPositionSumCalculator).getDiscountType();
        verifyNoMoreInteractions(quantityDiscountPositionSumCalculator, noDiscountPositionSumCalculator);
    }
}