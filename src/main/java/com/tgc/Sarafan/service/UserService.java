package com.tgc.Sarafan.service;

import com.tgc.Sarafan.domain.User;

public interface UserService {

    User findById(String id);

    boolean createUser(User user);
}
