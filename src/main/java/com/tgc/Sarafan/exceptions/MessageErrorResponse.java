package com.tgc.Sarafan.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageErrorResponse {

    private String message;
    private long timestamp;
}
