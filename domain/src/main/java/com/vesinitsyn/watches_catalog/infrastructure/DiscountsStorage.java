package com.vesinitsyn.watches_catalog.infrastructure;

import com.vesinitsyn.watches_catalog.domain.model.Discount;

import java.util.Map;
import java.util.Set;

public interface DiscountsStorage {
    Map<String, Discount> findByWatchIds(Set<String> watchIds);
}
