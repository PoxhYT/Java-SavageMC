package com.rosemite.services.backend.http;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.rosemite.services.helper.Log;
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
        Map<String, String> headers = new HashMap<>();

        return request(type, body, path, headers);
    }

    public HttpResponse request(HttpType type, Map<String, Object> body, Path path, Map<String, String> headers) throws IOException {
        if (body == null) {
            body = new HashMap<>();
        } else {
            String bodyData = new Gson().toJson(body);
            body.clear();

            body.put("data", bodyData);
        }

        if (headers == null) {
            headers = new HashMap<>();
        }

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

    public void reportError() {
        // TODO: Report a error & save it to Firebase
    }

    public String toString(Map<String, Object> value) {
       return new Gson().toJson(value, value.getClass());
    }
}