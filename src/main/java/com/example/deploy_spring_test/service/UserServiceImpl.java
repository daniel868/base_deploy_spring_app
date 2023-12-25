package com.example.deploy_spring_test.service;

import com.example.deploy_spring_test.entity.User;
import com.example.deploy_spring_test.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public User addUser(String username) {
        logger.debug("Inserting user with username {}", username);
        User user = new User();
        user.setCreatedDate(new Date());
        user.setUsername(username);
        return userRepository.save(user);
    }

    @Override
    public List<User> geUsers() {
        logger.debug("Loading all users from database");
        return userRepository.findAll();
    }
}
