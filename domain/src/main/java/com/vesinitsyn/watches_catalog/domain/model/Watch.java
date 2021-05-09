package com.vesinitsyn.watches_catalog.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@Accessors(chain = true)
@RequiredArgsConstructor
public class Watch {
    private final String id;
    private final String name;
    private final BigDecimal price;
}
