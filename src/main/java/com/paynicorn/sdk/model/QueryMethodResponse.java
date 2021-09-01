package com.paynicorn.sdk.model;

import lombok.Data;

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
        private float supportAmount[];
        private float minAmount;
        private float maxAmount;
        private float discount;
    }
}
