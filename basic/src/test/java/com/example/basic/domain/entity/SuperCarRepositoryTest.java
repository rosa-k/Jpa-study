package com.example.basic.domain.entity;

import com.example.basic.repository.SuperCarRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
@Transactional
@Rollback(false)
public class SuperCarRepositoryTest {

    @Autowired
    private SuperCarRepository superCarRepository;

    @Test
    void countTest(){
        log.info("total: " + superCarRepository.count());
    }

    @Test
    void findBySuperCarReleaseDateTest() {
        superCarRepository.findBySuperCarReleaseDate(LocalDateTime.of(2019, 12, 4, 0, 0))
                .stream().map(SuperCar::toString).forEach(log::info);
    }

    @Test
    void findBySuperCarColorContaining() {
        superCarRepository.findBySuperCarColorContaining("e").stream().map(SuperCar::toString).forEach(log::info);
    }

    @Test
    void findBySuperCarReleaseDateBetween() {
        LocalDateTime start = LocalDateTime.of(2018, 12, 31, 0, 0);
        LocalDateTime end = LocalDateTime.of(2019, 12, 31, 0, 0);
        superCarRepository.findBySuperCarReleaseDateBetween(start, end)
                .stream().map(SuperCar::toString).forEach(log::info);
    }

    @Test
    void findAll() {
//        page는 0부터 시작한다.
        Page<SuperCar> superCars = superCarRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.DESC, "superCarId")));
        superCars.getNumber();
        superCars.getTotalPages();
        superCars.hasNext();
        superCars.hasPrevious();
        superCars.isFirst();
        superCars.isLast();
//                .getContent().stream().map(SuperCar::toString).forEach(log::info);
    }

    @Test
    public void findTop10OrderBySuperCarIdDescTest(){
        superCarRepository.findTop10ByOrderBySuperCarIdDesc().stream().map(SuperCar::toString).forEach(log::info);
    }

    @Test
    void update() {
    }
}



















