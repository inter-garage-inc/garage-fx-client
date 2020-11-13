package app.client;

import app.service.AuthenticationService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GarageClient {
    public final static String HOST = "http://localhost:8080";

    public static HttpResponse<?> get(String resource) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var request = HttpRequest.newBuilder()
                .GET()
                .header("Content-Type", "application/json")
                .uri(URI.create(HOST + resource))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<?> delete(String resource) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var request = HttpRequest.newBuilder()
                .DELETE()
                .header("Content-Type", "application/json")
                .uri(URI.create(HOST + resource))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<?> post(String resource, Object payload) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString((String) payload))
                .header("Content-Type", "application/json")
                .setHeader("Authorization", "Bearer " + AuthenticationService.getToken())
                .uri(URI.create(HOST + resource))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<?> put(String resource, Object payload) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        var request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString((String) payload))
                .header("Content-Type", "application/json")
                .uri(URI.create(HOST + resource))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
