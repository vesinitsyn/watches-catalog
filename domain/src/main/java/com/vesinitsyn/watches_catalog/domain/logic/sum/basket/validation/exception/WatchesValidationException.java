package com.vesinitsyn.watches_catalog.domain.logic.sum.basket.validation.exception;

import java.util.Set;

public class WatchesValidationException extends RuntimeException {

    public WatchesValidationException(Set<String> foundWatchIds,
                                      Set<String> expectedWatchIds) {
        super(String.format(
                "Not all watches found in catalog: ( foundWatchIds: %s, expectedWatchIds: %s) ",
                foundWatchIds,
                expectedWatchIds
        ));
    }
}
