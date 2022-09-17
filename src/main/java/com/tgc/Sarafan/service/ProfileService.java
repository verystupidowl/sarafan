package com.tgc.Sarafan.service;

import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.UserSubscription;
import com.tgc.Sarafan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    @Autowired
    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subscriptions = channel.getSubscribers()
                .stream()
                .filter(subscription ->
                        subscription.getSubscriber().equals(subscriber)
                ).toList();
        if (subscriptions.isEmpty()) {
            UserSubscription subscription = new UserSubscription();
            subscription.setSubscriber(subscriber);
            subscription.setChannel(channel);
            channel.getSubscribers().add(subscription);
        } else {
            subscriptions.forEach(channel.getSubscribers()::remove);
        }
        return userRepository.save(channel);
    }
}
