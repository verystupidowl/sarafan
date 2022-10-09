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
    private String recipientId;
    private String senderId;
    private String userpic;
    private NotificationType notificationType;
}
