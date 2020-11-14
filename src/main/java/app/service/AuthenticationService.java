package app.service;

import app.client.GarageClient;
import app.data.Credentials;
import app.data.Jwt;
import app.data.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;

import java.io.IOException;

public class AuthenticationService {

    private static Jwt jwt;

    private final ObjectMapper mapper;

    public AuthenticationService() {
        mapper = new ObjectMapper();
    }

    public Boolean login(Credentials authRequest) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(authRequest);
            var response = GarageClient.post("/authentication", payload);
            if(response.statusCode() == 200) {
                jwt = mapper.readValue((String) response.body(), new TypeReference<Jwt>() {});
                return true;
            }
            return false;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public static Jwt getJwt() {
        return jwt;
    }
}
