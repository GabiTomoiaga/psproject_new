package com.example.restserviceclient.model.repository;

import com.example.restserviceclient.model.entity.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class StockRepository {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api/stock";

    public List<Stock> getAllByStore(int storeId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/store/" + storeId))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Arrays.asList(mapper.readValue(response.body(), Stock[].class));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean add(Stock s) {
        try {
            String json = mapper.writeValueAsString(s);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Stock getStockByStoreAndPerfume(int storeId, int perfumeId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/check?storeId=" + storeId + "&perfumeId=" + perfumeId))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
                return mapper.readValue(response.body(), Stock.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
