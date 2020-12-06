package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Order;
import app.data.Plan;
import app.data.order.PaymentMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * This class is responsible for the Garage Inc. orders transactions
 * Can get orders, create a new one (check in), update it (checkout) or delete it.
 *
 * @author FelipePy
 * @author jlucasrods
 * @version 1.0
 * @since 2020-11-13
 */
public class OrdersService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private ObjectMapper mapper;

    public OrdersService() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * This method is used to create a new Order
     *
     * @param order POJO
     * @return a Order POJO if is successfully created or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Order create(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders", payload);
            System.out.println(response.body());
            return response.statusCode() == 201
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException(exception);
        }
    }

    /**
     * This method is used to get an open order by license plate of check in
     *
     * @param licensePlate POJO
     * @return a Order POJO if a open order is successfully found or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Order findOpenByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders/open/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
            return response.statusCode() == 200
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    /**
     * This method is used to check out an open order
     *
     * @param order POJO
     * @return true if the order is successfully closed or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean close(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders/close", payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    /**
     * This method is used to update an order
     *
     * @param id Order id
     * @param order POJO
     * @return true if the order is successfully updated or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean update(Long id, Order order) throws  ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.put("/orders"+id, payload);
            System.out.println(response.body());
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    /**
     * This method is used to get all existing orders, be open, closed or canceled
     *
     * @return a List of Order POJOs
     * @throws ConnectionFailureException when request to the server fails
     */
    public List<Order> findAll() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders");
            return mapper.readValue((String) response.body(), new TypeReference<List<Order>>() {});
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
