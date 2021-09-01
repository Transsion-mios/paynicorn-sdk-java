package com.paynicorn.sdk;

import com.google.gson.Gson;
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

    private static String queryMethodUrl = "https://api.paynicorn.com/trade/v3/transaction/method";


    public static InitPaymentResponse initPayment(String appKey, String merchantSecret, InitPaymentRequest initPaymentRequest) throws IOException {
        Gson gson = new Gson();

        String jsonStr = gson.toJson(initPaymentRequest);
        byte[] datautf8 = jsonStr.getBytes("utf-8");
        String base64str = Base64.encodeBase64String(datautf8);
        //md5 sign

        String signStr = base64str+merchantSecret;

        String md5sign = DigestUtils.md5Hex(signStr);
        HttpPost http = new HttpPost(paymentUrl);
        Request request = new Request();
        request.setContent(base64str);
        request.setAppKey(appKey);
        request.setSign(md5sign);
        http.setHeader("Content-Type","application/json");
        StringEntity entity = new StringEntity(gson.toJson(request),"UTF-8");
        http.setEntity(entity);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(http);
        String res = EntityUtils.toString(httpResponse.getEntity());

        Response response = gson.fromJson(res,Response.class);
        if(response != null && "000000".equalsIgnoreCase(response.getResponseCode())){
            String content = response.getContent();
            String decodeContent = new String(Base64.decodeBase64(content.getBytes()));
            InitPaymentResponse initPaymentResponse = gson.fromJson(decodeContent, InitPaymentResponse.class);
            return initPaymentResponse;
        }else {
            return null;
        }
    }

    public static QueryPaymentResponse queryPayment(String appKey, String merchantSecret, QueryPaymentRequest queryPaymentRequest) throws IOException {
        Gson gson = new Gson();

        String jsonstr = gson.toJson(queryPaymentRequest);

        //base64 encode
        byte[] datautf8 = jsonstr.getBytes("utf-8");
        String base64str = Base64.encodeBase64String(datautf8);
        //md5 sign

        String signStr = base64str+merchantSecret;

        String md5sign = DigestUtils.md5Hex(signStr);
        HttpPost http = new HttpPost(queryPaymentUrl);
        Request request = new Request();
        request.setContent(base64str);
        request.setAppKey(appKey);
        request.setSign(md5sign);
        http.setHeader("Content-Type","application/json");
        StringEntity entity = new StringEntity(gson.toJson(request),"UTF-8");
        http.setEntity(entity);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(http);
        String res = EntityUtils.toString(httpResponse.getEntity());

        Response response = gson.fromJson(res,Response.class);
        if(response != null && "000000".equalsIgnoreCase(response.getResponseCode())){
            String content = response.getContent();
            String decodeContent = new String(Base64.decodeBase64(content.getBytes()));
            return gson.fromJson(decodeContent,QueryPaymentResponse.class);
        }else {
            return null;
        }
    }


    public static PostbackInfo receivePostback(String merchantSecret, String body){

        Gson gson = new Gson();

        PostbackRequest request = gson.fromJson(body,PostbackRequest.class);

        String base64Content = request.getContent();
        String sign = request.getSign();

        String signStr = base64Content+merchantSecret;
        String md5sign = DigestUtils.md5Hex(signStr);
        if (md5sign.equals(sign)){
            byte[] contentBytes = Base64.decodeBase64(base64Content);
            String content = new String(contentBytes);
            PostbackInfo info = gson.fromJson(content, PostbackInfo.class);
            info.setVerified(true);
            return info;
        }else{
            PostbackInfo info = new PostbackInfo();
            info.setVerified(false);
            return info;
        }
    }

    public static QueryMethodResponse queryMethod(String appKey, String merchantSecret, QueryMethodRequest queryMethodRequest) throws IOException{
        Gson gson = new Gson();

        String jsonstr = gson.toJson(queryMethodRequest);

        //base64 encode
        byte[] datautf8 = jsonstr.getBytes("utf-8");
        String base64str = Base64.encodeBase64String(datautf8);
        //md5 sign

        String signStr = base64str+merchantSecret;

        String md5sign = DigestUtils.md5Hex(signStr);
        HttpPost http = new HttpPost(queryMethodUrl);
        Request request = new Request();
        request.setContent(base64str);
        request.setAppKey(appKey);
        request.setSign(md5sign);
        http.setHeader("Content-Type","application/json");
        StringEntity entity = new StringEntity(gson.toJson(request),"UTF-8");
        http.setEntity(entity);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = httpClient.execute(http);
        String res = EntityUtils.toString(httpResponse.getEntity());

        Response response = gson.fromJson(res,Response.class);
        if(response != null && "000000".equalsIgnoreCase(response.getResponseCode())){
            String content = response.getContent();
            String decodeContent = new String(Base64.decodeBase64(content.getBytes()));
            QueryMethodResponse queryMethodResponse = gson.fromJson(decodeContent,QueryMethodResponse.class);
            return queryMethodResponse;
        }else {
            return null;
        }
    }
}
