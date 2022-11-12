package com.tgc.Sarafan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageErrorResponse {

    private String message;
    private long timestamp;
}
