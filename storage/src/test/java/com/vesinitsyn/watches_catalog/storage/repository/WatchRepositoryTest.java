package com.vesinitsyn.watches_catalog.storage.repository;

import com.vesinitsyn.watches_catalog.domain.model.Watch;
import com.vesinitsyn.watches_catalog.infrastructure.WatchesStorage;
import com.vesinitsyn.watches_catalog.storage.entity.WatchEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.BigDecimalComparator.BIG_DECIMAL_COMPARATOR;

class WatchRepositoryTest extends AbstractTest {

    @Autowired
    private WatchesStorage watchRepository;

    @Test
    void findByIds_WhenNoWatchesExist_ShouldReturnEmptyList() {
        // Given
        // When
        Set<Watch> actualWatches = watchRepository.findByIds(Set.of("001", "002", "003"));

        // Then
        assertThat(actualWatches).isEmpty();
    }

    @Test
    void findByIds_WhenAllSearchingWatchesExist_ShouldReturnExpectedExistingWatches() {
        // Given
        String watchId1 = "001";
        String name1 = "Rolex";
        BigDecimal price1 = BigDecimal.valueOf(100D);
        String watchId2 = "003";
        String name2 = "Swatch";
        BigDecimal price2 = BigDecimal.valueOf(50D);

        WatchEntity watchEntity1 = persistWatch(watchId1, name1, price1);
        WatchEntity watchEntity2 = persistWatch(watchId2, name2, price2);
        flushAndClear();

        Watch expectedWatch1 = new Watch(
                watchId1,
                name1,
                price1
        );
        Watch expectedWatch2 = new Watch(
                watchId2,
                name2,
                price2
        );
        Set<Watch> expectedWatches = Set.of(expectedWatch1, expectedWatch2);

        // When
        Set<Watch> actualWatches = watchRepository.findByIds(Set.of(watchId1, watchId2));

        // Then
        assertThat(actualWatches)
                .usingFieldByFieldElementComparator()
                .usingComparatorForElementFieldsWithType(BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .containsExactlyInAnyOrderElementsOf(expectedWatches);
    }

    @Test
    void findByIds_WhenNotAllSearchingWatchesExist_ShouldReturnOnlyExistingWatches() {
        // Given
        String watchId1 = "001";
        String name1 = "Rolex";
        BigDecimal price1 = BigDecimal.valueOf(100D);
        String watchId2 = "003";
        String name2 = "Swatch";
        BigDecimal price2 = BigDecimal.valueOf(50D);

        WatchEntity watchEntity1 = persistWatch(watchId1, name1, price1);
        WatchEntity watchEntity2 = persistWatch(watchId2, name2, price2);
        flushAndClear();

        Watch expectedWatch1 = new Watch(
                watchId1,
                name1,
                price1
        );
        Watch expectedWatch2 = new Watch(
                watchId2,
                name2,
                price2
        );
        Set<Watch> expectedWatches = Set.of(expectedWatch1, expectedWatch2);

        // When
        Set<Watch> actualWatches = watchRepository.findByIds(Set.of(watchId1, watchId2, "notExistingWatchId"));

        // Then
        assertThat(actualWatches)
                .usingFieldByFieldElementComparator()
                .usingComparatorForElementFieldsWithType(BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .containsExactlyInAnyOrderElementsOf(expectedWatches);
    }
}