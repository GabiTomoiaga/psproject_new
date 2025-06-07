package com.example.restserviceclient.model.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.restserviceclient.model.entity.Perfume;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PerfumeRepository {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api/perfumes";

    public List<Perfume> getAll() {
        try {
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder().uri(URI.create(baseUrl)).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            return Arrays.asList(mapper.readValue(response.body(), Perfume[].class));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Perfume getById(Integer id) {
        try {
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder().uri(URI.create(baseUrl + "/" + id)).GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            return mapper.readValue(response.body(), Perfume.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Perfume> searchByName(String name) {
        try {
            HttpResponse<String> response = client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(baseUrl + "/search?name=" + name))
                            .GET().build(),
                    HttpResponse.BodyHandlers.ofString()
            );
            return Arrays.asList(mapper.readValue(response.body(), Perfume[].class));
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean addPerfume(Perfume perfume) {
        try {
            String json = mapper.writeValueAsString(perfume);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updatePerfume(Perfume perfume) {
        try {
            String json = mapper.writeValueAsString(perfume);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletePerfume(Integer id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/" + id))
                    .DELETE().build();
            return client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Perfume> filterPerfumes(String brand, Integer storeId, Double maxPrice) {
        try {
            StringBuilder url = new StringBuilder(baseUrl + "/filter?");
            if (brand != null && !brand.isEmpty()) url.append("brand=").append(URLEncoder.encode(brand, StandardCharsets.UTF_8)).append("&");
            if (storeId != null) url.append("storeId=").append(storeId).append("&");
            if (maxPrice != null) url.append("maxPrice=").append(maxPrice);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url.toString()))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Arrays.asList(mapper.readValue(response.body(), Perfume[].class));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
