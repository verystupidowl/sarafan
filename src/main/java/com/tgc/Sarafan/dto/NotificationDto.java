package com.tgc.Sarafan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDto {

    private Long creationDate;
    private String username;
    private String channelId;
    private String authorId;
    private String userpic;
    private NotificationType notificationType;
}
