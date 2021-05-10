package com.vesinitsyn.watches_catalog.storage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "quantity_discount")
public class QuantityDiscountEntity {
    @Id
    @GeneratedValue(generator = "quantity_discount_seq")
    @SequenceGenerator(name = "quantity_discount_seq", sequenceName = "quantity_discount_seq")
    private Long id;
    @Column(name = "watch_id")
    private String watchId;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Column(name = "price")
    private BigDecimal price;
}
