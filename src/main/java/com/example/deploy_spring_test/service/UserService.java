package com.example.deploy_spring_test.service;

import com.example.deploy_spring_test.entity.User;

import java.util.List;

public interface UserService {
    User addUser(String username);
    List<User>geUsers();
}
