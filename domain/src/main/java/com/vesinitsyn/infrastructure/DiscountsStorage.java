package com.vesinitsyn.infrastructure;

import com.vesinitsyn.domain.model.Discount;

import java.util.Map;
import java.util.Set;

public interface DiscountsStorage {
    Map<String, Discount> findByWatchIds(Set<String> watchIds);
}
