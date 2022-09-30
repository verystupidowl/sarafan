package com.tgc.Sarafan.service.impl;

import com.tgc.Sarafan.domain.Comment;
import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.CommentDto;
import com.tgc.Sarafan.dto.EventType;
import com.tgc.Sarafan.dto.ObjectType;
import com.tgc.Sarafan.repositories.CommentRepository;
import com.tgc.Sarafan.service.CommentService;
import com.tgc.Sarafan.utils.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, CommentDto> wsSender;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, WebSocketSender webSocketSender) {
        this.wsSender = webSocketSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto create(CommentDto commentDto, User user) {
        commentDto.setAuthor(user);

        Message message = new Message(
                commentDto.getMessageId(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Comment comment = new Comment(
                commentDto.getId(),
                commentDto.getText(),
                message,
                commentDto.getAuthor()
        );

        Comment commentFromDb = commentRepository.save(comment);

        CommentDto commentDtoFromDb = new CommentDto(
                commentFromDb.getId(),
                commentFromDb.getText(),
                commentFromDb.getMessage().getId(),
                commentFromDb.getAuthor()
        );

        wsSender.accept(EventType.CREATE, commentDtoFromDb);


        return commentDtoFromDb;
    }
}
