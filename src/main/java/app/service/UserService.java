package app.service;

import app.client.GarageClient;
import app.data.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class UserService {

    private ObjectMapper mapper;

    public UserService() {
        mapper = new ObjectMapper();
    }

    public User findByToken(String token) throws IOException, InterruptedException {
        var response= GarageClient.get("/users/" + token);
        return mapper.readValue((String) response.body(), new TypeReference<User>(){});
    }

    public List<User> findAll() throws IOException, InterruptedException {
        var response= GarageClient.get("/users/");
        return mapper.readValue((String) response.body(), new TypeReference<List<User>>(){});
    }
}
