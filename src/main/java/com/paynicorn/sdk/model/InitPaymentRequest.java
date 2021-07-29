package com.paynicorn.sdk.model;

import lombok.Data;

import java.io.Serializable;

/**
 *          * request object example
 *          * {
 *          *     "amount": "30000",
 *          *     "countryCode": "ID",
 *          *     "cpFrontPage": "http://www.baidu.com",
 *          *     "currency": "IDR",
 *          *     "email": "111@111.com",
 *          *     "memo": "Memo",
 *          *     "orderDescription": "I am Test!",
 *          *     "payMethod": "DANA",
 *          *     "phone": "02219092345",
 *          *     "userId": "U20323123",
 *          *     "orderId":"TEST1609409844610"
 *          * }
 *
 * @author simon.sheng
 * @version 1.0.0
 * @since 1.8.0
 */
@Data
public class InitPaymentRequest implements Serializable {


    private static final long serialVersionUID = 1;

    private String amount;

    private String countryCode;

    private String cpFrontPage;

    private String currency;

    private String email;

    private String memo;

    private String orderDescription;

    private String payMethod;

    private String phone;

    private String userId;

    private String orderId;

}
