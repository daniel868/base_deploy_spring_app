package com.example.deploy_spring_test.service;

import com.example.deploy_spring_test.entity.Car;
import com.example.deploy_spring_test.repository.CarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public void saveCar(Car car) {
        carDao.saveCar(car);
    }
}
