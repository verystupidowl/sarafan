package com.tgc.Sarafan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserErrorResponse {

    private String message;
    private long timestamp;
}
