package com.tgc.Sarafan.service;

import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.UserSubscription;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProfileService {

    User changeSubscription(User channel, User subscriber);

    List<UserSubscription> getSubscribers(User channel);

    UserSubscription changeSubscriptionStatus(User channel, User subscriber);

    User findById(String id);

    List<User> findAll();

    @Transactional
    User changeNotifications(User user, User userFromDb);
}
