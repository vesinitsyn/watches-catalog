package com.vesinitsyn.storage.repository;

import com.vesinitsyn.domain.model.Watch;
import com.vesinitsyn.infrastructure.WatchesStorage;
import com.vesinitsyn.storage.entity.WatchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Repository
@RequiredArgsConstructor
class WatchRepository implements WatchesStorage {

    private final WatchCrudRepository watchCrudRepository;

    @Override
    public Set<Watch> findByIds(Set<String> watchIds) {
        return watchCrudRepository.findByIdIn(watchIds).stream()
                .map(this::convertToDomainModel)
                .collect(toSet());
    }

    private Watch convertToDomainModel(WatchEntity watchEntity) {
        return new Watch(
                watchEntity.getId(),
                watchEntity.getName(),
                watchEntity.getPrice()
        );
    }
}
