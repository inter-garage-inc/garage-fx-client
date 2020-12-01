package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class UsersService {

    private ObjectMapper mapper;

    public UsersService() {
        mapper = new ObjectMapper();
    }

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


    public Boolean userSave(User user) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(user);
            var response = GarageClient.post("/users", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean userDelete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/users/"+id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean userUpdate(Long id, User user) throws  ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(user);
            var response = GarageClient.put("/users/"+id, payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}