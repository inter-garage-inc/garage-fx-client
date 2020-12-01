package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OrdersService {
    private ObjectMapper mapper;

    public OrdersService() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public Boolean save(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException(exception);
        }
    }

    public Order findByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
            return response.statusCode() == 200
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
}
