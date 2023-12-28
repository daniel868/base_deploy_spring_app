package com.example.deploy_spring_test.aop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceAop {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceAop.class);

    @PersistenceContext
    EntityManager entityManager;

    @Before(value = "execution(* com.example.deploy_spring_test.service.UserServiceImpl.geUsers(..))")
    public void before() {
        Long countOfUsers = getCountOfUsers();
        logger.debug("Calling proxy for getUser() method; Database contains {} number of users", countOfUsers);
    }

    public Long getCountOfUsers() {
        TypedQuery<Long> query = entityManager.createQuery("select count(u) from ma_user u", Long.class);
        return  query.getSingleResult();
    }

}
