package com.vesinitsyn.storage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "watch")
public class WatchEntity {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
}
