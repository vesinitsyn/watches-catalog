package com.vesinitsyn.watches_catalog.infrastructure;

import com.vesinitsyn.watches_catalog.domain.model.Watch;

import java.util.Set;

public interface WatchesStorage {
    Set<Watch> findByIds(Set<String> watchIds);
}
