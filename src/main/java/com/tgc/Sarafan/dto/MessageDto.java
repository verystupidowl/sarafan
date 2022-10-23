package com.tgc.Sarafan.dto;

import com.tgc.Sarafan.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Message message;
    private List<String> recipientId;
}
