package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for the Garage Inc. users transactions
 *
 * @author jlucasrods
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-03
 */
public class UsersService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private ObjectMapper mapper;

    public UsersService() {
        mapper = new ObjectMapper();
    }

    /**
     * This method is used to get an user by they username
     *
     * @param username User username
     * @return a User POJO if existing one is found or null otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public User findByUsername(String username) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/users/find_by/" + username);
            if(response.statusCode() == 200) {
                return mapper.readValue((String) response.body(), new TypeReference<User>() {
                });
            } else {
                return null;
            }

        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to save a new user
     *
     * @param user POJO
     * @return true if the user is successfully saved of false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean userSave(User user) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(user);
            var response = GarageClient.post("/users", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to delete an user
     *
     * @param id User id
     * @return true if the user is successfully deleted of false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean userDelete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/users/"+id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to update an existing user
     *
     * @param id User id
     * @param user POJO
     * @return true if the user is successfully updated or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean userUpdate(Long id, User user) throws  ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(user);
            var response = GarageClient.put("/users/" + id, payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
