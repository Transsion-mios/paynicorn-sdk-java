package com.paynicorn.sdk.model;

import lombok.Data;

@Data
public class Response {
    private String responseCode;
    private String responseMessage;
    private String content;
    private String sign;
}
