package app.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.client.GarageClient;
import app.data.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Service
public class UserService implements Serializable {

    private ObjectMapper mapper;

    public User findBy(Long id) throws IOException, InterruptedException {
        mapper = new ObjectMapper();
        var response= GarageClient.get("/users/" + id);
        return mapper.readValue((String) response.body(), new TypeReference<User>(){});
    }

    public List<User> findAll() throws IOException, InterruptedException {
        mapper = new ObjectMapper();
        var response= GarageClient.get("/users/");
        return mapper.readValue((String) response.body(), new TypeReference<List<User>>(){});
    }

}
