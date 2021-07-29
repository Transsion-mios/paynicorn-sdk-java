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
public class PostbackInfo {
    private boolean verified;
    private String txnId;
    private String orderId;
    private String amount;
    private String currency;
    private String countryCode;
    private String status;
    private String code;
    private String message;
}
