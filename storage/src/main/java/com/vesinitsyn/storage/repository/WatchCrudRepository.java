package com.vesinitsyn.storage.repository;

import com.vesinitsyn.storage.entity.WatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

interface WatchCrudRepository extends JpaRepository<WatchEntity, String> {
    List<WatchEntity> findByIdIn(Set<String> watchIds);
}
