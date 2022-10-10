package com.tgc.Sarafan.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tgc.Sarafan.dto.EventType;
import com.tgc.Sarafan.dto.ObjectType;
import com.tgc.Sarafan.dto.WsEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class WebSocketSender {

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper;

    @Autowired
    public WebSocketSender(SimpMessagingTemplate template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class view) {
        ObjectWriter writer = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(view);

        return (EventType eventType, T payload) -> {
            String value;

            try {
                value = writer.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            if (objectType.equals(ObjectType.COMMENT) || objectType.equals(ObjectType.MESSAGE)) {
                template.convertAndSend(
                        "/messages-comments/activity",
                        new WsEventDto(objectType, eventType, value)
                );
            } else if (objectType.equals(ObjectType.NOTIFICATION)) {
                template.convertAndSend(
                        "/subscribe-notification/activity",
                        new WsEventDto(objectType, eventType, value)
                );
            }
        };
    }
}
