package com.tgc.Sarafan.dto;

import com.tgc.Sarafan.domain.User;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Comment text should not be empty!")
    @Size(min = 1, max = 50, message = "Comment text size should be between 1 and 50 characters")
    private String text;

    private long messageId;
    private User author;
}
