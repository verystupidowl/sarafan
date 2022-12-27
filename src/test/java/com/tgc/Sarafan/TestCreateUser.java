package com.tgc.Sarafan;

import com.tgc.Sarafan.repositories.UserRepository;
import com.tgc.Sarafan.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class TestCreateUser {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void addUser() {

    }

    @Test
    public void addFailedUser() {

    }
}
