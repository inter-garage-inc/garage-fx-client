package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Order;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OrdersService {

    private ObjectMapper mapper;

    public OrdersService() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
<<<<<<< HEAD
    }

    public Order create(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders", payload);
            return response.statusCode() == 201
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException(exception);
        }
    }

    public Order findOpenByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders/open/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
            System.out.println("/orders/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
=======
    }

    public Order create(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders", payload);
            System.out.println(response.statusCode());
            return response.statusCode() == 201
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException(exception);
        }
    }

    public Order findOpenByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders/open/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
            System.out.println(response.body());
>>>>>>> main
            return response.statusCode() == 200
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
}
