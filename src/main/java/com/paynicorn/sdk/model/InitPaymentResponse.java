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
public class InitPaymentResponse {

    private String code	;// MANDATORY response code represent current request is success response or not refer to https://www.paynicorn.com/#/docs 6.5

    private String message;//	OPTIONAL response code refer to https://www.paynicorn.com/#/docs 6.5

    private String txnId;//	OPTIONAL unique transaction id generate by paynicorn.you can use it to query your transaction status or wait for a postback

    private String status;//	OPTIONAL transaction status (1:for success；-1：processing；0：failure)

    private String webUrl;//	OPTIONAL paynicorn cashier uri
}
