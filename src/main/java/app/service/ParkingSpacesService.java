package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.parking.ParkingSpace;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for the Garage Inc. parking spaces transactions
 * Can get parking spaces, create a new one, update it or delete it.
 *
 * @author jlucasrods
 * @version 1.0
 * @since 2020-11-13
 */
public class ParkingSpacesService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private ObjectMapper mapper;

    public ParkingSpacesService() {
        mapper = new ObjectMapper();
    }

    /**
     * This method is used to get all existing parking spaces, be vacant, occupied or inactive
     *
     * @return a List of ParkingSpace POJOs
     * @throws ConnectionFailureException when request to the server fails
     */
    public List<ParkingSpace> index() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/parking-spaces");
            return response.statusCode() == 200 ?
                    mapper.readValue((String) response.body(), new TypeReference<List<ParkingSpace>>() {}) :
                    null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    /**
     * This method is used to create a new parking space
     *
     * @param parkingSpace POJO
     * @return true if is successfully saved or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean save(ParkingSpace parkingSpace) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(parkingSpace);
            var response = GarageClient.post("/parking-spaces", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    /**
     * This method is used to update a parking space
     *
     * @param id ParkingSpace id
     * @param parkingSpace POJO
     * @return true if is successfully updated or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean update(Long id, ParkingSpace parkingSpace) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(parkingSpace);
            var response = GarageClient.put("/parking-spaces/" + id, payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    /**
     * This method is used to delete a parking space
     *
     * @param id ParkingSpace id
     * @return true if is successfully deleted or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean delete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/parking-spaces/" + id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    /**
     * This method is used to get all vacant parking spaces
     *
     * @return a List of ParkingSpace POJOs
     * @throws ConnectionFailureException when request to the server fails
     */
    public List<ParkingSpace> findAllVacant() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/parking-spaces/all-vacant");
            return response.statusCode() == 200
                    ? mapper.readValue((String) response.body(), new TypeReference<List<ParkingSpace>>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
}
