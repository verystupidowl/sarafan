package com.tgc.Sarafan.service.impl;

import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.UserSubscription;
import com.tgc.Sarafan.exceptions.UserWithIdNotFoundException;
import com.tgc.Sarafan.repositories.UserRepository;
import com.tgc.Sarafan.repositories.UserSubscriptionRepository;
import com.tgc.Sarafan.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Autowired
    public ProfileServiceImpl(UserRepository userRepository, UserSubscriptionRepository userSubscriptionRepository) {
        this.userRepository = userRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    @Override
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

    @Override
    @Transactional
    public List<UserSubscription> getSubscribers(User channel) {
        return userSubscriptionRepository.findByChannel(channel);
    }

    @Override
    @Transactional
    public UserSubscription changeSubscriptionStatus(User channel, User subscriber) {
        UserSubscription subscription = userSubscriptionRepository.findByChannelAndSubscriber(channel, subscriber);
        subscription.setActive(!subscription.isActive());

        return userSubscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserWithIdNotFoundException("User with id " + id + " not found!"));
    }
}
