package com.rosemite.services.models.http;

import com.google.gson.Gson;

import java.util.Map;

public class HttpResponse {
    public final String content;
    public final int statusCode;

    public HttpResponse(String content, int statusCode) {
        this.content = content;
        this.statusCode = statusCode;
    }

    public Map<String, Object> getAsMap() {
        return new Gson().fromJson(content, Map.class);
    }
}
