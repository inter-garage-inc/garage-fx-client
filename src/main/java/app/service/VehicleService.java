package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Customer;
import app.data.Vehicle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class VehicleService {
    private ObjectMapper mapper;

    public VehicleService() {
        mapper = new ObjectMapper();
    }

    public Vehicle findByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/vehicles/license-plate/" + licensePlate);
            return response.statusCode() == 200 ?
                    mapper.readValue((String) response.body(), new TypeReference<Vehicle>() {}) :
                    null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean delete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/vehicles/" + id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
