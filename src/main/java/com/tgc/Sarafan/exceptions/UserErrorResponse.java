package com.tgc.Sarafan.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserErrorResponse {

    private String message;
    private long timestamp;
}
