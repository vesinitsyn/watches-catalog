package com.vesinitsyn.watches_catalog.storage.repository;

import com.vesinitsyn.watches_catalog.domain.model.Discount;
import com.vesinitsyn.watches_catalog.domain.model.QuantityDiscount;
import com.vesinitsyn.watches_catalog.infrastructure.DiscountsStorage;
import com.vesinitsyn.watches_catalog.storage.entity.QuantityDiscountEntity;
import com.vesinitsyn.watches_catalog.storage.entity.WatchEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.BigDecimalComparator.BIG_DECIMAL_COMPARATOR;

class DiscountRepositoryTest extends AbstractTest {

    @Autowired
    private DiscountsStorage discountsStorage;

    @Test
    void findByWatchIds_WhenNoSearchingWatchDiscountExists_ShouldReturnEmptyMap() {
        // Given
        // When
        Map<String, Discount> actualDiscounts = discountsStorage
                .findByWatchIds(Set.of("notExistingDiscountWatchId"));

        // Then
        assertThat(actualDiscounts).isEmpty();
    }

    @Test
    void findByWatchIds_WhenSearchingWatchDiscountExists_ShouldReturnIt() {
        // Given
        String watchId = "001";
        String name = "Rolex";
        BigDecimal price = BigDecimal.valueOf(100D);
        WatchEntity watchEntity = persistWatch(watchId, name, price);

        BigDecimal discountPrice = BigDecimal.valueOf(200D);
        BigDecimal discountQuantity = BigDecimal.valueOf(3L);
        QuantityDiscountEntity quantityDiscountEntity = new QuantityDiscountEntity()
                .setWatchId(watchId)
                .setPrice(discountPrice)
                .setQuantity(discountQuantity);
        entityManager.persist(quantityDiscountEntity);
        flushAndClear();

        Map<String, Discount> expectedDiscounts = Map.of(
                watchId, new QuantityDiscount(discountQuantity, discountPrice)
        );

        // When
        Map<String, Discount> actualDiscounts = discountsStorage
                .findByWatchIds(Set.of(watchId));

        // Then
        assertThat(actualDiscounts)
                .usingComparatorForType(BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .usingRecursiveComparison()
                .isEqualTo(expectedDiscounts);
    }

    @Test
    void findByWatchIds_WhenOneSearchingWatchDiscountExistsAndAnotherOneNot_ShouldReturnOnlyExistingOne() {
        // Given
        String watchId = "001";
        String name = "Rolex";
        BigDecimal price = BigDecimal.valueOf(100D);
        WatchEntity watchEntity = persistWatch(watchId, name, price);

        BigDecimal discountPrice = BigDecimal.valueOf(200D);
        BigDecimal discountQuantity = BigDecimal.valueOf(3L);
        QuantityDiscountEntity quantityDiscountEntity = new QuantityDiscountEntity()
                .setWatchId(watchId)
                .setPrice(discountPrice)
                .setQuantity(discountQuantity);
        entityManager.persist(quantityDiscountEntity);
        flushAndClear();

        Map<String, Discount> expectedDiscounts = Map.of(
                watchId, new QuantityDiscount(discountQuantity, discountPrice)
        );

        // When
        Map<String, Discount> actualDiscounts = discountsStorage
                .findByWatchIds(Set.of(watchId, "notExistingDiscountWatchId"));

        // Then
        assertThat(actualDiscounts)
                .usingComparatorForType(BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .usingRecursiveComparison()
                .isEqualTo(expectedDiscounts);
    }
}