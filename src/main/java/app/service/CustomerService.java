package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CustomerService {

    private final ObjectMapper mapper;

    public CustomerService() {
        mapper = new ObjectMapper();
    }

    public Customer register(Customer customer) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(customer);
            var response = GarageClient.post("/customers", payload);
            if(response.statusCode() == 201) {
                return mapper.readValue((String) response.body(), new TypeReference<Customer>(){});
            }
            return null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Customer findByCpfCnpj(String cpfCnpj) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/customers/cpf-cnpj/" + cpfCnpj);
            if(response.statusCode() == 200) {
                return mapper.readValue((String) response.body(), new TypeReference<Customer>() {});
            }
            return null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Customer findByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/customers/license-plate/" + licensePlate);
            if(response.statusCode() == 200) {
                return mapper.readValue((String) response.body(), new TypeReference<Customer>() {});
            }
            return null;
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