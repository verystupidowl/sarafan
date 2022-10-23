package com.tgc.Sarafan.service.impl;

import com.tgc.Sarafan.domain.Comment;
import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.*;
import com.tgc.Sarafan.exceptions.NotFoundException;
import com.tgc.Sarafan.repositories.CommentRepository;
import com.tgc.Sarafan.repositories.MessageRepository;
import com.tgc.Sarafan.service.CommentService;
import com.tgc.Sarafan.utils.WebSocketSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;


@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, CommentDto> wsSender;
    private final BiConsumer<EventType, NotificationDto> wsSenderAnswer;
    private final MessageRepository messageRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, WebSocketSender webSocketSender, WebSocketSender webSocketSenderAnswer, MessageRepository messageRepository) {
        this.wsSender = webSocketSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
        this.commentRepository = commentRepository;
        this.wsSenderAnswer = webSocketSenderAnswer.getSender(ObjectType.NOTIFICATION, Views.FullComment.class);
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
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

        sendToWs(user, message, commentDtoFromDb);

        return commentDtoFromDb;
    }

    private void sendToWs(User user, Message message, CommentDto commentDtoFromDb) {
        Optional<Message> optionalMessage = messageRepository.findById(message.getId());
        Message messageToFindAuthorId = optionalMessage.orElseThrow(NotFoundException::new);
        List<String> singletonList = Collections.singletonList(messageToFindAuthorId.getAuthor().getId());
        if (!singletonList.contains(user.getId())) {
            NotificationDto notificationDto = new NotificationDto(
                    System.currentTimeMillis(),
                    user.getName(),
                    singletonList,
                    user.getId(),
                    user.getUserpic(),
                    NotificationType.COMMENT_ANSWER
            );
            wsSenderAnswer.accept(EventType.CREATE, notificationDto);
        }

        wsSender.accept(EventType.CREATE, commentDtoFromDb);
    }
}
