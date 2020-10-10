package com.rosemtie.services.backend.http;

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

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class Http {
    public String request(String url, HttpType type, Map<String, Object> params) throws IOException {

        HttpURLConnection httpClient = (HttpURLConnection)new URL(url).openConnection();

        httpClient.setRequestMethod(type.toString());
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = httpClient.getResponseCode();
        StringBuilder response = new StringBuilder();

        try (BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }

    public Map<String, Object> toMap(String value) {
        Map<String, Object> x = new Gson().fromJson(value, Map.class);
        return x;
    }

    public String toString(Map<String, Object> value) {
       return new Gson().toJson(value, value.getClass());
    }
}
