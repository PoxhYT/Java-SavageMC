package com.rosemite.services.backend.http;

//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.rosemite.services.models.HttpResponse;
import com.rosemite.services.models.Path;
import okhttp3.*;

public class Http {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String key;
    private final String url;

    public Http(String key, String url) {
        this.key = key;
        this.url = url;
    }

    public HttpResponse request(HttpType type, Map<String, Object> body, Path path) throws IOException {
        if (body == null) {
            body = new HashMap<>();
        } else {
            String bodyData = new Gson().toJson(body);
            body.clear();

            body.put("data", bodyData);
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("path", path.get());
        headers.put("key", key);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .build();

        RequestBody requestBody = RequestBody.create(JSON, toString(body));

        if (type == HttpType.GET) {

            request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .build();

            Response response = client.newCall(request).execute();

            return new HttpResponse(response.body().string(), response.code());
        }

        switch (type) {
            case POST:
                request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .post(requestBody)
                    .build();
                break;
            case PUT:
                request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .put(requestBody)
                    .build();
                break;
            case DELETE:
                request = new Request.Builder()
                    .url(url)
                    .headers(Headers.of(headers))
                    .delete(requestBody)
                    .build();
                break;
        }

        Response response = client.newCall(request).execute();

        return new HttpResponse(response.body().string(), response.code());
    }

    public Map<String, Object> toMap(String value) {
        Map<String, Object> x = new Gson().fromJson(value, Map.class);
        return x;
    }

    public String toString(Map<String, Object> value) {
       return new Gson().toJson(value, value.getClass());
    }
}