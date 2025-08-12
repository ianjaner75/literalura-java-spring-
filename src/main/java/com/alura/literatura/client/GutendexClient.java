package com.alura.literatura.client;

import com.alura.literatura.client.dto.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GutendexClient {
    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public GutendexResponse search(String title) {
        try {
            String q = URLEncoder.encode(title, StandardCharsets.UTF_8);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create("https://gutendex.com/books/?search=" + q))
                    .GET()
                    .build();
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(res.body(), GutendexResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error consultando Gutendex", e);
        }
    }
}
