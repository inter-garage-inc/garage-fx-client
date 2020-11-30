package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CustomersService {

    private final ObjectMapper mapper;

    public CustomersService() {
        mapper = new ObjectMapper();
    }

    public Customer register(Customer customer) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(customer);
            var response = GarageClient.post("/customers", payload);
            return response.statusCode() == 201 ?
                mapper.readValue((String) response.body(), new TypeReference<Customer>(){}) :
                null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Customer findByCpfCnpj(String cpfCnpj) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/customers/cpf-cnpj/" + URLEncoder.encode(cpfCnpj, StandardCharsets.UTF_8));
            return response.statusCode() == 200 ?
                mapper.readValue((String) response.body(), new TypeReference<Customer>() {}) :
                null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Customer findByVehicle(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/customers/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
            return response.statusCode() == 200 ?
                mapper.readValue((String) response.body(), new TypeReference<Customer>() {}) :
                null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Customer update(Long id, Customer customer) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(customer);
            var response = GarageClient.put("/customers/" + id, payload);
            return response.statusCode() == 200 ?
                mapper.readValue((String) response.body(), new TypeReference<Customer>() {}) :
                null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean delete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/customers/" + id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}