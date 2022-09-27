package com.tgc.Sarafan.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentErrorResponse {

    private String message;
    private long timestamp;
}
