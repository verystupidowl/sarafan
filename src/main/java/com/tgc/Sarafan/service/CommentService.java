package com.tgc.Sarafan.service;

import com.tgc.Sarafan.domain.User;
import com.tgc.Sarafan.dto.CommentDto;

public interface CommentService {

    CommentDto create(CommentDto commentDto, User user);
}
