package com.tgc.Sarafan.service.impl;

import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.UserSubscription;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.EventType;
import com.tgc.Sarafan.dto.NotificationDto;
import com.tgc.Sarafan.dto.NotificationType;
import com.tgc.Sarafan.dto.ObjectType;
import com.tgc.Sarafan.exceptions.UserWithIdNotFoundException;
import com.tgc.Sarafan.repositories.UserRepository;
import com.tgc.Sarafan.repositories.UserSubscriptionRepository;
import com.tgc.Sarafan.service.ProfileService;
import com.tgc.Sarafan.utils.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final BiConsumer<EventType, NotificationDto> wsSender;

    @Autowired
    public ProfileServiceImpl(UserRepository userRepository, UserSubscriptionRepository userSubscriptionRepository,
                              WebSocketSender webSocketSender) {
        this.userRepository = userRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.wsSender = webSocketSender.getSender(ObjectType.NOTIFICATION, Views.FullProfile.class);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
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

            sendToWs(channel, subscriber);
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

    @Override
    @Transactional
    public User changeNotifications(User user, User userFromDb) {
        userFromDb.setNotificationTypes(user.getNotificationTypes());
        return userRepository.save(userFromDb);
    }

    private void sendToWs(User channel, User subscriber) {
        NotificationDto dto = new NotificationDto(
                new Date().getTime(),
                subscriber.getName(),
                Collections.singletonList(channel.getId()),
                subscriber.getId(),
                subscriber.getUserpic(),
                NotificationType.SUBSCRIBE
        );

        wsSender.accept(EventType.CREATE, dto);
    }
}
