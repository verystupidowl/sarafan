package com.tgc.Sarafan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationDto {

    private Long creationDate;
    private String username;
    private List<String> recipientId;
    private String senderId;
    private String userpic;
    private NotificationType notificationType;
}
