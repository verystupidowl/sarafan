package com.tgc.Sarafan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDto {

    private String username;
    private String channelId;
    private String subscriberId;
    private String userpic;
    private NotificationType type;
}
