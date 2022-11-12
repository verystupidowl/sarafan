package com.tgc.Sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.UserSubscription;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.utils.UserErrorResponse;
import com.tgc.Sarafan.exceptions.UserWithIdNotFoundException;
import com.tgc.Sarafan.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("get-all")
    @JsonView(Views.FullProfile.class)
    public List<User> getUsers() {
        return profileService.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable String id) {
        return profileService.findById(id);
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(@AuthenticationPrincipal User subscriber, @PathVariable("channelId") User channel) {
        if (subscriber.equals(channel)) {
            return channel;
        }
        return profileService.changeSubscription(channel, subscriber);
    }

    @GetMapping("/get-subscriptions/{subscriptionId}")
    @JsonView(Views.FullProfile.class)
    public List<UserSubscription> subscriptions(@PathVariable("subscriptionId") User subscriber) {
        return profileService.getSubscriptions(subscriber);
    }

    @GetMapping("get-subscribers/{channelId}")
    @JsonView(Views.IdName.class)
    public List<UserSubscription> subscribers(
            @PathVariable("channelId") User channel
    ) {
        return profileService.getSubscribers(channel);
    }

    @PostMapping("change-status/{subscriberId}")
    @JsonView(Views.IdName.class)
    public UserSubscription changeSubscriptionStatus(
            @AuthenticationPrincipal User channel,
            @PathVariable("subscriberId") User subscriber
    ) {
        return profileService.changeSubscriptionStatus(channel, subscriber);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> exceptionHandler(UserWithIdNotFoundException e) {
        UserErrorResponse userErrorResponse = new UserErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(userErrorResponse, HttpStatus.NOT_FOUND);
    }
}
