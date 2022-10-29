package com.tgc.Sarafan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.domain.Views;
import com.tgc.Sarafan.dto.MessagePageDto;
import com.tgc.Sarafan.service.MessageService;
import com.tgc.Sarafan.service.UserService;
import com.tgc.Sarafan.service.impl.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    private final MessageService messageService;
    private final UserService userService;

    @Value("${spring.profile.active:prod}")
    private String profile;
    private final ObjectWriter messageWriter;
    private final ObjectWriter profileWriter;

    @Autowired
    public MainController(MessageServiceImpl messageService, UserService userService, ObjectMapper mapper) {
        this.messageService = messageService;
        this.userService = userService;
        this.messageWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullMessage.class);

        this.profileWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        Map<Object, Object> data = new HashMap<>();

        if (user != null) {
            data = extracted(model, user);
        } else {
            model.addAttribute("messages", "[]");
            model.addAttribute("profile", "null");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }

    private Map<Object, Object> extracted(Model model, User user) throws JsonProcessingException {
        Map<Object, Object> data = new HashMap<>();
        User userFromDb = userService.findById(user.getId());
        String serializedProfile = profileWriter.writeValueAsString(userFromDb);
        model.addAttribute("profile", serializedProfile);

        PageRequest pageRequest = getPageRequest();
        MessagePageDto messagePageDto = messageService.findForUser(pageRequest, user);

        String messages = messageWriter.writeValueAsString(messagePageDto.getMessages());

        model.addAttribute("messages", messages);
        data.put("currentPage", messagePageDto.getCurrentPage());
        data.put("totalPages", messagePageDto.getTotalPage());

        return data;
    }

    private PageRequest getPageRequest() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return PageRequest.of(0, MessageController.MESSAGES_PER_PAGE, sort);
    }
}