package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.User;
import app.data.authentication.Credentials;
import app.data.authentication.Jwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;

import java.io.IOException;

/**
 * This class is responsible for handling user authentication.
 * Send credentials to the server and gets a token that can be used to access restricted resources.
 *
 * @author jlucasrods
 * @version 1.0
 * @since 2020-11-03
 */
public class AuthenticationService {

    /**
     * The Json Web Token received on successfully authentication Represents the authentication state itself, if present the user is authenticated, otherwise they is not.
     */
    private static Jwt jwt;

    /**
     * A cache to the authenticated user account
     */
    private static User user;

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private final ObjectMapper mapper;

    public AuthenticationService() {
        mapper = new ObjectMapper();
    }

    /**
     * This method is used to run the authentication routine and get an authorization token
     *
     * @param credentials a POJO
     * @return true on authenticated successfully or false otherwise
     * @throws ConnectionFailureException when the request to the server fails
     */
    public Boolean login(Credentials credentials) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(credentials);
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

    /**
     * This is a helper method that loads the data to the authenticated user account
     * @return An object of authenticated user or null if an error occurs
     * @throws ConnectionFailureException when the request to the server fails
     */
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

    /**
     * @return the cached user account if they is authenticated
     */
    public static User claimUser() {
        return user;
    }

    /**
     * @return the authorization token of authenticated user, if the are
     */
    public static String getAuthorization() {
        return (jwt != null) ? "Bearer " + jwt.getToken() : "";
    }

    /**
     * Log out of the authenticated user
     */
    public static void logout() {
        jwt = null;
        user = null;
    }
}
