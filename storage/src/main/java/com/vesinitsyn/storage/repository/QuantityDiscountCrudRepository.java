package com.vesinitsyn.storage.repository;

import com.vesinitsyn.storage.entity.QuantityDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuantityDiscountCrudRepository extends JpaRepository<QuantityDiscountEntity, String> {
    List<QuantityDiscountEntity> findAllByWatchIdIn(Set<String> watchIds);
}
