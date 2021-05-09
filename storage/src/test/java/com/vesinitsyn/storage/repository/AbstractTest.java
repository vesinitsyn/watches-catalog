package com.vesinitsyn.storage.repository;

import com.vesinitsyn.storage.StorageConfiguration;
import com.vesinitsyn.storage.entity.WatchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@DataJpaTest
@Import(StorageConfiguration.class)
public class AbstractTest {

    @Autowired
    protected EntityManager entityManager;

    protected void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    protected WatchEntity persistWatch(String watchId, String name, BigDecimal price) {
        WatchEntity watchEntity = new WatchEntity()
                .setId(watchId)
                .setName(name)
                .setPrice(price);
        entityManager.persist(watchEntity);
        return watchEntity;
    }
}
