package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Customer;
import app.data.Vehicle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This class is responsible for the Garage Inc. vehicles transactions
 */
public class VehiclesService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private ObjectMapper mapper;

    public VehiclesService() {
        mapper = new ObjectMapper();
    }

    /**
     * This method is used to get an existing vehicle by its license plate
     *
     * @param licensePlate Vehicle license plate
     * @return a Vehicle POJO if one is successfully found or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
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

    /**
     * This method is used to save a new vehicle
     *
     * @param vehicle POJO
     * @return a Vehicle POJO if is successfully saved or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Vehicle save(Vehicle vehicle) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(vehicle);
            var response = GarageClient.post("/vehicles/", payload);
            return response.statusCode() == 201
                    ? mapper.readValue((String) response.body(), new TypeReference<Vehicle>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to delete a vehicle
     *
     * @param id Vehicle id
     * @return true if is successfully deleted or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean delete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/vehicles/" + id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
