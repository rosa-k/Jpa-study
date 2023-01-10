package com.example.basic.domain.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class SuperCarEntityTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void superCarTest() {
        SuperCar superCarA = new SuperCar();
        SuperCar superCarB = new SuperCar();

        superCarA.setSuperCarName("Porsche911");
        superCarA.setSuperCarColor("blue");

        superCarB.setSuperCarName("Genesis");
        superCarB.setSuperCarColor("red");

        entityManager.persist(superCarA);
        entityManager.persist(superCarB);

        entityManager.flush();

        entityManager.clear();

        SuperCar findSuperCar1 = entityManager.find(SuperCar.class, 1L);
        SuperCar findSuperCar2 = entityManager.find(SuperCar.class, 1L);

        findSuperCar2.setSuperCarColor("Black");

        entityManager.remove(findSuperCar2);

        entityManager.flush();
        entityManager.clear();

        if(Optional.ofNullable(findSuperCar1).isPresent()){
            assertThat(findSuperCar1.getSuperCarName()).isEqualTo("Porsche911");
//            SELECT 쿼리 flush 후 1차 캐시에 저장된 객체를 다시 조회하면 값 비교는 항상 true이다.
            assertThat(findSuperCar1).isEqualTo(findSuperCar2);
        }

    }

}
