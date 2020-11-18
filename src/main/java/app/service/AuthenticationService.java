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

    public static String getAuthorization() {
        return (jwt != null) ? "Bearer " + jwt.getToken() : "";
    }

    private static String getUnsignedToken() {
        var token = jwt.getToken();
        return token.substring(0, token.lastIndexOf('.') + 1);
    }

    @SneakyThrows
    public static User claimUser() {
        var claims = Jwts.parser().parseClaimsJwt(getUnsignedToken()).getBody();
        var mapper = new ObjectMapper();
        return mapper.readValue(claims.get("user", String.class), User.class);
    }

    public static void logout() {
        jwt = null;
    }
}
