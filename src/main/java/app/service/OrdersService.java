package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OrdersService {
    private ObjectMapper mapper;

    public OrdersService() {
        mapper = new ObjectMapper();
    }

    public Order findByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
            System.out.println((String) response.body());
            return response.statusCode() == 200
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
}
