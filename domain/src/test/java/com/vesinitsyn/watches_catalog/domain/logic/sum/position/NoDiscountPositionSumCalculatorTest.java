package com.vesinitsyn.watches_catalog.domain.logic.sum.position;

import com.vesinitsyn.watches_catalog.domain.model.DiscountType;
import com.vesinitsyn.watches_catalog.domain.model.NoDiscount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.BigDecimalComparator.BIG_DECIMAL_COMPARATOR;

@ExtendWith(MockitoExtension.class)
class NoDiscountPositionSumCalculatorTest {

    @InjectMocks
    private NoDiscountPositionSumCalculator noDiscountPositionSumCalculator;

    @Test
    void getDiscountType_WhenCalled_ShouldReturnExpectedValue() {
        // Given
        // When
        DiscountType actualDiscountType = noDiscountPositionSumCalculator.getDiscountType();

        // Then
        Assertions.assertSame(DiscountType.NO_DISCOUNT, actualDiscountType);
    }

    @ParameterizedTest
    @MethodSource("noDiscountProvider")
    void calculate_WhenCalled_ShouldReturnSumAsMultiplicationOfPriceAndQuantity(
            // Given
            BigDecimal price,
            BigDecimal quantity,
            BigDecimal expectedPositionSum
    ) {
        // When
        BigDecimal actualPositionSum = noDiscountPositionSumCalculator.calculate(
                price, quantity, NoDiscount.getInstance()
        );

        // Then
        assertThat(actualPositionSum)
                .usingComparator(BIG_DECIMAL_COMPARATOR)
                .isEqualTo(expectedPositionSum);
    }

    public static Object[][] noDiscountProvider() {
        BigDecimal price = BigDecimal.valueOf(50D);
        return new Object[][]{
                {
                        price,
                        BigDecimal.ZERO,
                        BigDecimal.ZERO
                },
                {
                        price,
                        BigDecimal.ONE,
                        price
                },
                {
                        price,
                        BigDecimal.valueOf(3D),
                        BigDecimal.valueOf(150D)
                }
        };
    }
}