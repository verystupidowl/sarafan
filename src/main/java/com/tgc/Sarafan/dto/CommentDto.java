package com.tgc.Sarafan.dto;

import com.tgc.Sarafan.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private long messageId;
    private User author;
}
