package com.vesinitsyn.domain.logic.sum.position;

import com.vesinitsyn.domain.model.QuantityDiscount;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.BigDecimalComparator.BIG_DECIMAL_COMPARATOR;

@ExtendWith(MockitoExtension.class)
class QuantityDiscountPositionSumCalculatorTest {

    @InjectMocks
    private QuantityDiscountPositionSumCalculator quantityDiscountPositionSumCalculator;

    @ParameterizedTest
    @MethodSource("quantityDiscountProvider")
    void calculate_WhenCalled_ShouldReturnExpectedPositionSum(
            // Given
            BigDecimal price,
            BigDecimal quantity,
            BigDecimal expectedPositionSum
    ) {
        // When
        QuantityDiscount discount = new QuantityDiscount(
                BigDecimal.valueOf(3D),
                BigDecimal.valueOf(200D)
        );
        BigDecimal actualPositionSum = quantityDiscountPositionSumCalculator.calculate(
                price, quantity, discount
        );

        // Then
        assertThat(actualPositionSum)
                .usingComparator(BIG_DECIMAL_COMPARATOR)
                .isEqualTo(expectedPositionSum);
    }

    public static Object[][] quantityDiscountProvider() {
        BigDecimal price = BigDecimal.valueOf(100D);
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
                        BigDecimal.valueOf(2D),
                        BigDecimal.valueOf(200D)
                },
                {
                        price,
                        BigDecimal.valueOf(3D),
                        BigDecimal.valueOf(200D)
                },
                {
                        price,
                        BigDecimal.valueOf(4D),
                        BigDecimal.valueOf(300D)
                },
                {
                        price,
                        BigDecimal.valueOf(5D),
                        BigDecimal.valueOf(400D)
                },
                {
                        price,
                        BigDecimal.valueOf(6D),
                        BigDecimal.valueOf(400D)
                },
                {
                        price,
                        BigDecimal.valueOf(7D),
                        BigDecimal.valueOf(500D)
                }
        };
    }
}