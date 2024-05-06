package com.zeal.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WikiCustomHttpClient implements CustomHttpClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String baseUrl;

    public WikiCustomHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public HttpResponse<String> sendRequestWithParam(String path, String pathParam) throws IOException, InterruptedException {
        return sendRequest(path, pathParam);
    }

    private HttpResponse<String> sendRequest(String path, String param) throws IOException, InterruptedException {
        String encodedParam = URLEncoder.encode(param, StandardCharsets.UTF_8);
        String fullPath = baseUrl + String.format(path, encodedParam);
        URI uri = URI.create(fullPath);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Expected status code 200 OK, but got " + (response.statusCode()));
        }
        
        return response;
    }
}