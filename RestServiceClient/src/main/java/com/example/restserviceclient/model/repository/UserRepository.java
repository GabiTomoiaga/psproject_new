package com.example.restserviceclient.model.repository;

import com.example.restserviceclient.model.entity.Users;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class UserRepository {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String baseUrl = "http://localhost:8080/api/users";

    public List<Users> getAll() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/users"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Arrays.asList(mapper.readValue(response.body(), Users[].class));
        } catch (Exception e) {
            e.printStackTrace(); // asigură-te că vezi erorile
            return List.of();
        }
    }


    public Users getById(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/" + id))
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return mapper.readValue(response.body(), Users.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Users> filterByRole(String role) {
        try {
            URI uri = URI.create(baseUrl + "/filter?role=" + role);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return Arrays.asList(mapper.readValue(response.body(), Users[].class));
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean addUser(Users user) {
        try {
            String json = mapper.writeValueAsString(user);
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

    public boolean updateUser(Users user) {
        try {
            String json = mapper.writeValueAsString(user);
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

    public boolean deleteUser(int id) {
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
