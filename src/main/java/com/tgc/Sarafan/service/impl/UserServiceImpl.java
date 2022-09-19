package com.tgc.Sarafan.service.impl;

import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.exceptions.UserWithIdNotFoundException;
import com.tgc.Sarafan.repositories.UserRepository;
import com.tgc.Sarafan.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserWithIdNotFoundException("User with this is not found"));
        Hibernate.initialize(user.getSubscriptions());
        return user;
    }
}
