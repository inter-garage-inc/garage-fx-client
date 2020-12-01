package app.service;

import app.client.GarageClient;
import app.data.User;
import app.data.authentication.Credentials;
import app.data.authentication.Jwt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;

import java.io.IOException;

public class AuthenticationService {

    private static Jwt jwt;

    private static User user;

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
                user = loadUser();
                return true;
            }
            return false;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    private User loadUser() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/authentication");
            return response.statusCode() == 200
                ? user = mapper.readValue((String) response.body(), new TypeReference<User>() {})
                : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public static User claimUser() {
        return user;
    }

    public static String getAuthorization() {
        return (jwt != null) ? "Bearer " + jwt.getToken() : "";
    }

    public static void logout() {
        jwt = null;
        user = null;
    }
}
