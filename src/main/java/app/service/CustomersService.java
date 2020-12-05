package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Customer;
import app.data.Vehicle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * This class is responsible for the Garage Inc. Customers transactions
 * Can get customers, save a new one, update it or delete it.
 *
 * @author jlucasrods
 * @version 1.0
 * @since 2020-11-13
 */
public class CustomersService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private final ObjectMapper mapper;

    public CustomersService() {
        mapper = new ObjectMapper();
    }

    /**
     * This method is used to save a new Customer
     *
     * @param customer POJO
     * @return a Customer POJO if is successfully saved or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Customer save(Customer customer) throws ConnectionFailureException {
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

    /**
     * This method is used to get a Customer by they CPF or CNPJ
     *
     * @param cpfCnpj Customer CPF or CNPJ
     * @return a Customer POJO if they is successfully found one or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
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

    /**
     * This method is used to get a Customer by the Vehicle when there are one
     *
     * @param licensePlate license plate of the Customer Vehicle
     * @return a Customer POJO if successfully found one or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Customer findByVehicle(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/customers/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
            return response.statusCode() == 200
                ? mapper.readValue((String) response.body(), new TypeReference<Customer>() {})
                : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to update a Customer
     *
     * @param id Customer id
     * @param customer POJO
     * @return a Customer POJO if is successfully updated or null otherwise
     * @throws ConnectionFailureException
     */
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

    /**
     * This method is used to delete a Customer
     *
     * @param id Customer id
     * @return true if is successfully deleted of false otherwise
     * @throws ConnectionFailureException
     */
    public Boolean delete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/customers/" + id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}