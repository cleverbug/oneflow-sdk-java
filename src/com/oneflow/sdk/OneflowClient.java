package com.oneflow.sdk;

import com.oneflow.sdk.models.Order;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;

public class OneflowClient {
    protected String endpoint;
    protected String token;
    protected String secret;
    protected Order order;

    public OneflowClient(String url, String tokenInit, String secretInit) {
        endpoint = url;
        token = tokenInit;
        secret = secretInit;
    }

    public Order createOrder(String destination) {
        order = new Order();
        order.getDestination().setName(destination);
        return order;
    }

    private HttpResponse request(String method, String resourcePath, String data) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String postUrl = endpoint + resourcePath;
        URL parseUrl = new URL(postUrl);
        String pathname = parseUrl.getPath();

        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        String authHeader = makeToken(method, pathname, timestamp.toString());

        String s = method.toLowerCase();
        if (s.equals("post")) {
            return postRequest(postUrl, data, timestamp, authHeader);
        }
        if (s.equals("put")) {
            return putRequest(postUrl, data, timestamp, authHeader);
        }
        if (s.equals("get")) {
            return getRequest(postUrl, timestamp, authHeader);
        }
        if (s.equals("delete")) {
            return deleteRequest(postUrl, timestamp, authHeader);
        }
        return null;
    }

    private HttpResponse request(String method, String resourcePath) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        return request(method, resourcePath, "");
    }

    private HttpResponse getRequest(String url, Timestamp timestamp, String authHeader) throws IOException {
        RequestConfig.Builder requestBuilder = RequestConfig.custom();

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());

        HttpClient client = builder.build();
        HttpGet get = new HttpGet(url);

        get.setHeader("x-oneflow-date", timestamp.toString());
        get.setHeader("x-oneflow-authorization", authHeader);

        HttpResponse response = client.execute(get);
        return response;
    }

    private HttpResponse postRequest(String url, String data, Timestamp timestamp, String authHeader) throws IOException {
        RequestConfig.Builder requestBuilder = RequestConfig.custom();

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());

        HttpClient client = builder.build();
        HttpPost post = new HttpPost(url);

        post.setHeader("Content-Type", "application/json");
        post.setHeader("x-oneflow-date", timestamp.toString());
        post.setHeader("x-oneflow-authorization", authHeader);

        post.setEntity(new StringEntity(data));
        HttpResponse response = client.execute(post);
        return response;
    }

    private HttpResponse putRequest(String url, String data, Timestamp timestamp, String authHeader) throws IOException {
        RequestConfig.Builder requestBuilder = RequestConfig.custom();

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());

        HttpClient client = builder.build();
        HttpPut put = new HttpPut(url);

        put.setHeader("Content-Type", "application/json");
        put.setHeader("x-oneflow-date", timestamp.toString());
        put.setHeader("x-oneflow-authorization", authHeader);

        put.setEntity(new StringEntity(data));
        HttpResponse response = client.execute(put);
        return response;
    }

    private HttpResponse deleteRequest(String url, Timestamp timestamp, String authHeader) throws IOException {
        RequestConfig.Builder requestBuilder = RequestConfig.custom();

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setDefaultRequestConfig(requestBuilder.build());

        HttpClient client = builder.build();
        HttpDelete delete = new HttpDelete(url);

        delete.setHeader("x-oneflow-date", timestamp.toString());
        delete.setHeader("x-oneflow-authorization", authHeader);

        HttpResponse response = client.execute(delete);
        return response;
    }

    public String makeToken(String method, String path, String timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = method + " " + path + " " + timestamp;
        Mac hmac = Mac.getInstance("HmacSHA1");
        hmac.init(new SecretKeySpec(secret.getBytes(Charset.forName("UTF-8")), "HmacSHA1"));
        hmac.update(stringToSign.getBytes(Charset.forName("UTF-8")));
        return token + ":" + bytesToHex(hmac.doFinal());
    }

    public HttpResponse validateOrder() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        Gson gson = new Gson();
        return request("POST", "/order/validate", gson.toJson(order));
    }

    public HttpResponse submitOrder() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        Gson gson = new Gson();
        return request("POST", "/order", gson.toJson(order));
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder buf = new StringBuilder();
        for (byte aB : b) {
            buf.append(hexDigit[(aB >> 4) & 0x0f]);
            buf.append(hexDigit[aB & 0x0f]);
        }
        return buf.toString();
    }
}
