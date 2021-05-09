package com.vesinitsyn.storage.repository;

import com.vesinitsyn.storage.StorageConfiguration;
import com.vesinitsyn.storage.entity.WatchEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@DataJpaTest
@EnableAutoConfiguration
@Import(AbstractTest.TestConfiguration.class)
@EntityScan(basePackages = "com.vesinitsyn.storage.entity")
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

    @SpringBootConfiguration
    @Import(StorageConfiguration.class)
    public static class TestConfiguration {
    }
}
