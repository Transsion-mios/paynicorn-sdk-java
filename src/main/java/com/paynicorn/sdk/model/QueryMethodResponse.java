package com.paynicorn.sdk.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QueryMethodResponse {

    private String code;

    private String message;

    private MethodInfo methodInfo[];

    @Data
    class MethodInfo {
        private String code;
        private String name;
        private String icon;
        private String methodType;
        private BigDecimal supportAmount[];
        private BigDecimal minAmount;
        private BigDecimal maxAmount;
        private BigDecimal discount;
    }
}
