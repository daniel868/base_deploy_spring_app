package com.example.deploy_spring_test.rest;

import com.example.deploy_spring_test.entity.Car;
import com.example.deploy_spring_test.entity.User;
import com.example.deploy_spring_test.service.CarService;
import com.example.deploy_spring_test.service.CarServiceImpl;
import com.example.deploy_spring_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/")
public class RestApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public Object fetchData() {
        Map<String, String> data = new HashMap<>();
        data.put("testData1", "mockData1");
        data.put("testData2", "mockData1");
        data.put("testData3", "mockData1");
        data.put("testData4", "mockData1");
        return data;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Object saveUser(@RequestParam("username") String username) {
        Map<String, String> data = new HashMap<>();
        User savedUser = userService.addUser(username);
        data.put("STATUS", (savedUser != null ? "SUCCESS" : "FAILED"));
        return data;
    }

    @RequestMapping(value = "/car", method = RequestMethod.POST)
    public Object saveCar(@RequestParam("carDescription") String carDescription,
                          @RequestParam("userId") Long userId
    ) {
        User userById = userService.findUserById(userId);
        Car car = new Car();
        car.setCarDescription(carDescription);
        carService.saveCar(car);
        userById.addNewCar(car);

        User savedUser = userService.updateUser(userById);
        Map<String, String> data = new HashMap<>();
        data.put("STATUS", (savedUser != null ? "SUCCESS" : "FAILED"));
        return data;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Object getUsers() {
        List<String> userWithId = userService.geUsers().stream()
                .map(user -> user.getUsername() + "_" + user.getId())
                .toList();
        SortedSet<String> sortedSet = new TreeSet<>(Comparator.reverseOrder());
        sortedSet.addAll(userWithId);
        return sortedSet;
    }

}
