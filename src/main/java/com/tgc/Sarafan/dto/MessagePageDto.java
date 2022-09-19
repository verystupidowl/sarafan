package com.tgc.Sarafan.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.Views;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonView(Views.FullMessage.class)
public class MessagePageDto {

    private List<Message> messages;
    private int currentPage;
    private int totalPage;
}
