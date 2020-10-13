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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.github.jlabsys.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.rosemite.services.helper.Log;
import com.rosemite.services.models.HttpResponse;
import javafx.util.Pair;
import okhttp3.*;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;

public class Http {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public HttpResponse request(String url, HttpType type, Map<String, Object> body, Map<String, String> headers) throws IOException {
        if (body == null) {
            body = new HashMap<>();
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .build();;

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