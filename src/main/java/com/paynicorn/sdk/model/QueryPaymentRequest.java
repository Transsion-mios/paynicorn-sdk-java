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
public class QueryPaymentRequest {
    private String orderId;//MANDATORY unique transaction id generate by paynicorn.you can use it to query your transaction status or wait for a postback
    private String txnType;
}
