package com.tgc.Sarafan.dto;

import com.tgc.Sarafan.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Comment text should not be empty!")
    @Size(min = 1, max = 20, message = "Comment text size should be between 1 and 20 characters")
    private String text;

    private long messageId;
    private User author;
}
