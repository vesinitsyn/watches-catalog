package com.vesinitsyn.infrastructure;

import com.vesinitsyn.domain.model.Watch;

import java.util.Set;

public interface WatchesStorage {
    Set<Watch> findByIds(Set<String> watchIds);
}
