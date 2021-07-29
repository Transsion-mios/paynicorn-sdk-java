package com.paynicorn.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.paynicorn.sdk.model.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;

/**
 * paynicorn logic
 *
 * @author simon.sheng
 * @version 1.0.0
 * @since 1.8.0
 */
public class Paynicorn {


    public static class TxnType {
        public static String PAYMENT = "payment";
        public static String PAYOUT = "payout";
        public static String AUTHPAY = "authpay";
        public static String REFUND = "refund";
    }

    private static String paymentUrl = "https://api.paynicorn.com/trade/v3/transaction/pay";

    private static String queryPaymentUrl = "https://api.paynicorn.com/trade/v3/transaction/query";


    public static InitPaymentResponse initPayment(String appKey, String merchantSecret, InitPaymentRequest initPaymentRequest) throws IOException {

        String jsonStr = JSON.toJSONString(initPaymentRequest);
        byte[] datautf8 = jsonStr.getBytes("utf-8");
        String base64str = Base64.encodeBase64String(datautf8);
        //md5 sign

        String signStr = base64str+merchantSecret;

        String md5sign = DigestUtils.md5Hex(signStr);
        HttpPost http = new HttpPost(paymentUrl);
        JSONObject request = new JSONObject();
        request.put("content",base64str);
        request.put("sign",md5sign);
        request.put("appKey",appKey);
        http.setHeader("Content-Type","application/json");
        StringEntity entity = new StringEntity(request.toJSONString(),"UTF-8");
        http.setEntity(entity);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(http);
        String res = EntityUtils.toString(response.getEntity());
        JSONObject resjson = JSON.parseObject(res);

        if("000000".equalsIgnoreCase(resjson.getString("responseCode"))){
            String content = resjson.getString("content");
            String decodeContent = new String(Base64.decodeBase64(content.getBytes()));
            InitPaymentResponse initPaymentResponse = JSON.parseObject(decodeContent, InitPaymentResponse.class);
            return initPaymentResponse;
        }else {
            return null;
        }
    }

    public static QueryPaymentResponse queryPayment(String appKey, String merchantSecret, QueryPaymentRequest queryPaymentRequest) throws IOException {
        String jsonstr = JSON.toJSONString(queryPaymentRequest);

        //base64 encode
        byte[] datautf8 = jsonstr.getBytes("utf-8");
        String base64str = Base64.encodeBase64String(datautf8);
        //md5 sign

        String signStr = base64str+merchantSecret;

        String md5sign = DigestUtils.md5Hex(signStr);
        HttpPost http = new HttpPost(queryPaymentUrl);
        JSONObject request = new JSONObject();
        request.put("content",base64str);
        request.put("sign",md5sign);
        request.put("appKey",appKey);
        http.setHeader("Content-Type","application/json");
        StringEntity entity = new StringEntity(request.toJSONString(),"UTF-8");
        http.setEntity(entity);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(http);
        String res = EntityUtils.toString(response.getEntity());
        JSONObject resjson = JSON.parseObject(res);
        if("000000".equalsIgnoreCase(resjson.getString("responseCode"))){
            String content = resjson.getString("content");
            String decodeContent = new String(Base64.decodeBase64(content.getBytes()));
            return JSON.parseObject(decodeContent,QueryPaymentResponse.class);
        }else {
            return null;
        }
    }


    public static PostbackInfo receivePostback(String merchantSecret, String body){

        PostbackRequest request = JSON.parseObject(body,PostbackRequest.class);

        String base64Content = request.getContent();
        String sign = request.getSign();

        String signStr = base64Content+merchantSecret;
        String md5sign = DigestUtils.md5Hex(signStr);
        if (md5sign.equals(sign)){
            byte[] contentBytes = Base64.decodeBase64(base64Content);
            String content = new String(contentBytes);
            PostbackInfo info = JSON.parseObject(content, PostbackInfo.class);
            info.setVerified(true);
            return info;
        }else{
            PostbackInfo info = new PostbackInfo();
            info.setVerified(false);
            return info;
        }
    }
}
