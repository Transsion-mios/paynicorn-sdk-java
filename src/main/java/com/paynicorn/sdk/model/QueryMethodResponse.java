package com.paynicorn.sdk.model;

import lombok.Data;

@Data
public class QueryMethodResponse {

    private String code;

    private String message;

    private MethodInfo methodInfo[];

    @Data
    class MethodInfo {
        private String mode;
        private String note;
    }
}
