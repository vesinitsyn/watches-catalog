package com.vesinitsyn.watches_catalog.storage.repository;

import com.vesinitsyn.watches_catalog.domain.model.Discount;
import com.vesinitsyn.watches_catalog.domain.model.QuantityDiscount;
import com.vesinitsyn.watches_catalog.infrastructure.DiscountsStorage;
import com.vesinitsyn.watches_catalog.storage.entity.QuantityDiscountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor
class DiscountRepository implements DiscountsStorage {

    private final QuantityDiscountCrudRepository quantityDiscountCrudRepository;

    @Override
    public Map<String, Discount> findByWatchIds(Set<String> watchIds) {
        return quantityDiscountCrudRepository.findAllByWatchIdIn(watchIds).stream()
                .collect(
                        toMap(
                                QuantityDiscountEntity::getWatchId,
                                this::convertToDomainModel
                        )
                );
    }

    private QuantityDiscount convertToDomainModel(QuantityDiscountEntity quantityDiscountEntity) {
        return new QuantityDiscount(
                quantityDiscountEntity.getQuantity(),
                quantityDiscountEntity.getPrice()
        );
    }
}
