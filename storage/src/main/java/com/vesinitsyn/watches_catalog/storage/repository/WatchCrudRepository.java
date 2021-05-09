package com.vesinitsyn.watches_catalog.storage.repository;

import com.vesinitsyn.watches_catalog.storage.entity.WatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

interface WatchCrudRepository extends JpaRepository<WatchEntity, String> {
    List<WatchEntity> findByIdIn(Set<String> watchIds);
}
