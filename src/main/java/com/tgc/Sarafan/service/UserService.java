package com.tgc.Sarafan.service;

import com.tgc.Sarafan.model.User;
import com.tgc.Sarafan.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public User findById(String id) {
        User user = userRepository.findById(id).get();
        Hibernate.initialize(user.getSubscriptions());
        return user;
    }
}
