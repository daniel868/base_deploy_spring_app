package com.example.deploy_spring_test.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserServiceAop {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceAop.class);

    @Before(value = "execution(* com.example.deploy_spring_test.service.UserServiceImpl.geUsers(..))")
    public void before() {
        logger.debug("Calling proxy for getUser() method");
    }

}
