package com.vesinitsyn.watches_catalog.domain.logic.sum.basket.validation;

import com.vesinitsyn.watches_catalog.domain.logic.sum.basket.validation.exception.WatchesValidationException;
import com.vesinitsyn.watches_catalog.domain.model.Watch;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
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
