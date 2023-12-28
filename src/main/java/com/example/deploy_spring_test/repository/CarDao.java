package com.example.deploy_spring_test.repository;

import com.example.deploy_spring_test.entity.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CarDao {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void saveCar(Car car) {
        if (car.getId() == null) {
            entityManager.persist(car);
        } else {
            entityManager.merge(car);
        }
    }
}
