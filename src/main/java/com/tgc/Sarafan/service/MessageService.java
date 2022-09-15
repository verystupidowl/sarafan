package com.tgc.Sarafan.service;

import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.dto.MessagePageDto;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface MessageService {

    void delete(Message message);

    Message update(Message messageFromDb, Message message) throws IOException;

    Message create(Message message, User user) throws IOException;

    MessagePageDto findAll(Pageable pageable);

}
