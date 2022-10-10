package com.tgc.Sarafan.service.impl;

import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.UserSubscription;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.*;
import com.tgc.Sarafan.repositories.MessageRepository;
import com.tgc.Sarafan.repositories.UserSubscriptionRepository;
import com.tgc.Sarafan.service.MessageService;
import com.tgc.Sarafan.utils.WebSocketSender;
import org.hibernate.Hibernate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {


    private final static String URL_PATTERN = "https?:\\/\\/?[\\w\\d\\._\\-%\\/\\?=&#]+";
    private final static String IMAGE_PATTERN = "\\.(jpeg|jpg|gif|png)$";

    private static final Pattern URL_REGEX = Pattern.compile(URL_PATTERN, Pattern.CASE_INSENSITIVE);
    private static final Pattern IMAGE_REGEX = Pattern.compile(IMAGE_PATTERN, Pattern.CASE_INSENSITIVE);

    private final MessageRepository messageRepository;
    private final BiConsumer<EventType, Message> webSocketSenderMessage;
    private final BiConsumer<EventType, NotificationDto> webSocketSenderNotification;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, WebSocketSender webSocketSenderMessage,
                              UserSubscriptionRepository userSubscriptionRepository, WebSocketSender webSocketSenderNotification) {
        this.messageRepository = messageRepository;
        this.webSocketSenderMessage = webSocketSenderMessage.getSender(ObjectType.MESSAGE, Views.FullMessage.class);
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.webSocketSenderNotification = webSocketSenderNotification.getSender(ObjectType.NOTIFICATION, Views.FullMessage.class);
    }

    @Override
    @Transactional
    public void delete(Message message) {
        messageRepository.delete(message);
        webSocketSenderMessage.accept(EventType.REMOVE, message);
    }

    @Override
    @Transactional
    public Message update(Message messageFromDb, Message message) throws IOException {
        String messageText = message.getText();
        String messageFromDbText = messageFromDb.getText();

        if (messageText.equals(messageFromDbText)) {
            return messageFromDb;
        } else {
            return getUpdatedMessage(messageFromDb, message);
        }
    }

    @Override
    @Transactional
    public Message create(Message message, User user) throws IOException {
        message.setCreationDate(LocalDateTime.now());
        message.setEdited(false);
        fillMeta(message);
        message.setAuthor(user);
        Hibernate.initialize(user.getSubscribers());
        Hibernate.initialize(user.getSubscriptions());
        Message updatedMessage = messageRepository.save(message);
        webSocketSenderMessage.accept(EventType.CREATE, updatedMessage);
        List<String> collect = userSubscriptionRepository.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .map(User::getId)
                .collect(Collectors.toList());
        webSocketSenderNotification.accept(EventType.CREATE, new NotificationDto(
                System.currentTimeMillis(),
                user.getName(),
                collect,
                user.getId(),
                user.getUserpic(),
                NotificationType.NEW_POSTS
        ));
        return updatedMessage;
    }

    @Override
    @Transactional
    public MessagePageDto findForUser(Pageable pageable, User user) {
        List<User> channels = userSubscriptionRepository.findBySubscriber(user)
                .stream()
                .filter(UserSubscription::isActive)
                .map(UserSubscription::getChannel)
                .collect(Collectors.toList());

        channels.add(user);

        Page<Message> page = messageRepository.findByAuthorIn(channels, pageable);

        return new MessagePageDto(
                page.getContent(),
                pageable.getPageNumber(),
                page.getTotalPages()
        );
    }

    private Message getUpdatedMessage(Message messageFromDb, Message message) throws IOException {
        messageFromDb.setText(message.getText());
        messageFromDb.setEdited(true);
        messageFromDb.setEditedDate(LocalDateTime.now());
        fillMeta(messageFromDb);
        Message updatedMessage = messageRepository.save(messageFromDb);

        webSocketSenderMessage.accept(EventType.UPDATE, updatedMessage);
        return updatedMessage;
    }

    private void fillMeta(Message message) throws IOException {
        String text = message.getText();
        Matcher matcher = URL_REGEX.matcher(text);

        if (matcher.find()) {
            String url = text.substring(matcher.start(), matcher.end());

            matcher = IMAGE_REGEX.matcher(url);

            message.setLink(url);

            if (matcher.find()) {
                message.setLinkCover(url);
            } else if (!url.contains("youtu")) {
                MetaDto meta = getMeta(url);

                message.setLinkCover(meta.getCover());
                message.setLinkTitle(meta.getTitle());
                message.setLinkDescription(meta.getDescription());
            }
        }
    }

    private MetaDto getMeta(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        Elements title = doc.select("meta[name$=title],meta[property$=title]");
        Elements description = doc.select("meta[name$=description],meta[property$=description]");
        Elements cover = doc.select("meta[name$=image],meta[property$=image]");

        return new MetaDto(
                getContent(title.first()),
                getContent(description.first()),
                getContent(cover.first())
        );
    }

    private String getContent(Element element) {
        return element == null ? "" : element.attr("content");
    }
}
