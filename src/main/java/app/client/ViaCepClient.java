package app.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCepClient {
    public final static String HOST = "https://viacep.com.br/";

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
}
