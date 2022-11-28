package com.paynicorn.sdk.model;

import lombok.Data;

/**
 * @author duanyongqiang
 */
@Data
public class RechargeCallbackInfo {
    private String orderNo;
    private String originOrderNo;
    private Integer status;
    private Integer timestamp;
    private boolean verified;
}
