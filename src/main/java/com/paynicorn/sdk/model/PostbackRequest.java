package com.paynicorn.sdk.model;

import lombok.Data;

/**
 * TODO
 *
 * @author simon.sheng
 * @version 1.0.0
 * @since 1.8.0
 */
@Data
public class PostbackRequest {

    private String content;
    private String sign;
}
