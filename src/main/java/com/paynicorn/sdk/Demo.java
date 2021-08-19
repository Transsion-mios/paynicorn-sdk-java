package com.paynicorn.sdk;


import static spark.Spark.post;
import static spark.Spark.port;


import com.alibaba.fastjson.JSON;
import com.paynicorn.sdk.model.*;

import java.io.IOException;

/**
 * TODO
 *
 * @author simon.sheng
 * @version 1.0.0
 * @since 1.8.0
 */
public class Demo {
    private static final String appKey = "PUT_YOUR_APPKEY_HERE";
    private static final String merchantSecret = "PUT_YOUR_MERCHANT_SECRET_HERE";
    public static void main(String[] args) throws IOException {


        //raise a payment request to PAYNICORN
        InitPaymentRequest initPaymentRequest = new InitPaymentRequest();
        initPaymentRequest.setOrderId("PUT_YOUR_ORDER_ID_HERE");
        initPaymentRequest.setCountryCode("NG");
        initPaymentRequest.setCurrency("NGN");
        initPaymentRequest.setAmount("100");
        initPaymentRequest.setBankCode("NG001");
        initPaymentRequest.setPayMethod("USSD");
        initPaymentRequest.setCpFrontPage("PUT_YOUR_WEB_REDIRECT_URL_HERE");
        initPaymentRequest.setOrderDescription("TEST GOODS NAME");
        InitPaymentResponse initPaymentResponse = Paynicorn.initPayment(appKey,merchantSecret, initPaymentRequest);
        System.out.println(JSON.toJSONString(initPaymentResponse));


        //query a payment status from PAYNICORN
        QueryPaymentRequest queryPaymentRequest = new QueryPaymentRequest();
        queryPaymentRequest.setOrderId("PUT_YOUR_ORDER_ID_HERE");
        queryPaymentRequest.setTxnType(Paynicorn.TxnType.PAYMENT);
        QueryPaymentResponse queryPaymentResponse = Paynicorn.queryPayment(appKey,merchantSecret,queryPaymentRequest);
        System.out.println(JSON.toJSONString(queryPaymentResponse));


        //receive a payment status postback from PAYNICORN
        port(8080);
        post("/postback", (request, response) -> {
            String body = request.body();
            PostbackInfo info = Paynicorn.receivePostback(merchantSecret,body);

            if (info.isVerified()){
                System.out.println(JSON.toJSONString(info));

                //you need to response "success_"+TxnId in PostbackInfo to PAYNICORN if you receive the postback successfully
                //otherwise, PAYNICORN will continue to send the postback to your server unless you give that response
                response.status(200);
                return "success_"+info.getTxnId();
            }else{
                response.status(500);
                return "";
            }
        });
    }
}
