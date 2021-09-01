package com.paynicorn.sdk.model;

import lombok.Data;

@Data
public class Request {
    private String content;
    private String sign;
    private String appKey;
}
