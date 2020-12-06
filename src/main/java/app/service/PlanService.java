package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Plan;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for the Garage Inc. plans transactions Can get plans, create a new one, update it or delete it.
 *
 * @author ttarora
 * @version 1.0
 * @since 2020-12-01
 */
public class PlanService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private ObjectMapper mapper;

    public PlanService() {
        mapper = new ObjectMapper();
    }

    /**
     * This method is used to get all existing plans
     *
     * @return a List of Plan POJOs
     * @throws ConnectionFailureException when request to the server fails
     */
    public List<Plan> findAll() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/plans");
            return mapper.readValue((String) response.body(), new TypeReference<List<Plan>>() {});
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to get a plan by id
     *
     * @param id Plan id
     * @return a Plan POJO if the plan is successfully found or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Plan findById(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/plans/"+ id);
            return mapper.readValue((String) response.body(), new TypeReference<Plan>() {});
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to save a new plan
     *
     * @param plan POJO
     * @return true if is successfully saved or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean create(Plan plan) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(plan);
            var response = GarageClient.post("/plans", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to update an existing plan
     *
     * @param plan POJO
     * @param id Plan id
     * @return true if is successfully updated or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean update(Plan plan, Long id) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(plan);
            var response = GarageClient.put("/plans/"+id, payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to delete a plan
     *
     * @param id Plan id
     * @return true if is successfully deleted or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean delete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/plans/"+id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
