package com.paynicorn.sdk.model;

import lombok.Data;

@Data
public class QueryMethodRequest {
    private String countryCode;
    private String currency;
    private String txnType;
}
