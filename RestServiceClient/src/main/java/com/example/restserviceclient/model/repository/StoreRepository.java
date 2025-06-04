package com.example.restserviceclient.model.repository;

import com.example.restserviceclient.model.entity.Stores;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class StoreRepository {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api/stores";

    public List<Stores> getAll() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Arrays.asList(mapper.readValue(response.body(), Stores[].class));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Stores getById(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/" + id))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), Stores.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addStore(Stores store) {
        try {
            String json = mapper.writeValueAsString(store);
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

    public boolean updateStore(Stores store) {
        try {
            String json = mapper.writeValueAsString(store);
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

    public boolean deleteStore(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/" + id))
                    .DELETE().build();

            return client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
