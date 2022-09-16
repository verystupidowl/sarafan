package com.tgc.Sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    @JsonView(Views.FullProfile.class)
    public User get(@PathVariable("id") User user) {
        return user;
    }

    @PostMapping("change-subscription/{channelId}")
    @JsonView(Views.FullProfile.class)
    public User changeSubscription(@AuthenticationPrincipal User subscriber, @PathVariable("channelId") User channel) {
        if(subscriber.equals(channel)) {
            return channel;
        }
        return profileService.changeSubscription(channel, subscriber);
    }
}
