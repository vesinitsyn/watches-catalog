package com.vesinitsyn.domain.logic.sum.basket.validation;

import com.vesinitsyn.domain.logic.sum.basket.validation.exception.WatchesValidationException;
import com.vesinitsyn.domain.model.Watch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class WatchesValidationServiceTest {

    @InjectMocks
    private WatchesValidationService watchesValidationService;

    @Test
    void validate_WhenExpectedWatchesPassed_ShouldNotThrowAnyException() {
        // Given
        String watchId1 = "001";
        String watchId2 = "002";
        String watchId3 = "003";
        Watch watch1 = new Watch(watchId1, "Rolex", BigDecimal.valueOf(100D));
        Watch watch2 = new Watch(watchId2, "Michael Kors", BigDecimal.valueOf(80D));
        Watch watch3 = new Watch(watchId3, "Swatch", BigDecimal.valueOf(50D));

        Set<Watch> watches = Set.of(watch1, watch2, watch3);
        Set<String> expectedWatchIds = Set.of(watchId1, watchId2, watchId3);

        // When
        // Then
        assertDoesNotThrow(() -> watchesValidationService.validate(watches, expectedWatchIds));
    }

    @Test
    void validate_WhenNotAllWatchesPassed_ShouldThrowExpectedException() {
        // Given
        String watchId1 = "001";
        String watchId2 = "002";
        String watchId3 = "003";
        Watch watch1 = new Watch(watchId1, "Rolex", BigDecimal.valueOf(100D));
        Watch watch2 = new Watch(watchId2, "Michael Kors", BigDecimal.valueOf(80D));

        Set<Watch> watches = Set.of(watch1, watch2);
        Set<String> expectedWatchIds = Set.of(watchId1, watchId2, watchId3);

        // When
        // Then
        assertThrows(
                WatchesValidationException.class,
                () -> watchesValidationService.validate(watches, expectedWatchIds)
        );
    }

    @Test
    void validate_WhenExtraWatchesPassed_ShouldThrowExpectedException() {
        // Given
        String watchId1 = "001";
        String watchId2 = "002";
        String watchId3 = "003";
        Watch watch1 = new Watch(watchId1, "Rolex", BigDecimal.valueOf(100D));
        Watch watch2 = new Watch(watchId2, "Michael Kors", BigDecimal.valueOf(80D));
        Watch watch3 = new Watch(watchId3, "Swatch", BigDecimal.valueOf(50D));

        Set<Watch> watches = Set.of(watch1, watch2, watch3);
        Set<String> expectedWatchIds = Set.of(watchId1, watchId2);

        // When
        // Then
        assertThrows(
                WatchesValidationException.class,
                () -> watchesValidationService.validate(watches, expectedWatchIds)
        );
    }

    @Test
    void validate_WhenNotExpectedWatchPassed_ShouldThrowExpectedException() {
        // Given
        String watchId1 = "001";
        String watchId2 = "002";
        String watchId3 = "003";
        Watch watch1 = new Watch(watchId1, "Rolex", BigDecimal.valueOf(100D));
        Watch watch2 = new Watch(watchId2, "Michael Kors", BigDecimal.valueOf(80D));
        Watch notExpectedWatch = new Watch("notExpectedWatchId", "NotExpectedWatchName", BigDecimal.valueOf(50D));

        Set<Watch> watches = Set.of(watch1, watch2, notExpectedWatch);
        Set<String> expectedWatchIds = Set.of(watchId1, watchId2, watchId3);

        // When
        // Then
        assertThrows(
                WatchesValidationException.class,
                () -> watchesValidationService.validate(watches, expectedWatchIds)
        );
    }
}