package com.vesinitsyn.domain.logic.sum.basket.validation;

import com.vesinitsyn.domain.logic.sum.basket.validation.exception.WatchesValidationException;
import com.vesinitsyn.domain.model.Watch;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class WatchesValidationService {

    public void validate(Set<Watch> watches, Set<String> expectedWatchIds) {
        Set<String> foundWatchIds = watches.stream()
                .map(Watch::getId)
                .collect(toSet());
        if (foundWatchIds.size() != expectedWatchIds.size()) {
            throw new WatchesValidationException(foundWatchIds, expectedWatchIds);
        }
        if (!foundWatchIds.containsAll(expectedWatchIds)) {
            throw new WatchesValidationException(foundWatchIds, expectedWatchIds);
        }
    }
}
