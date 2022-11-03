package com.tgc.Sarafan.controller;

import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notifications")
public class NotificationController {

    private final ProfileService profileService;

    @Autowired
    public NotificationController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping("/change-notifications/{id}")
    public User changeNotifications(@RequestBody User user, @PathVariable("id") User userFromDb) {
        return profileService.changeNotifications(user, userFromDb);
    }
}
