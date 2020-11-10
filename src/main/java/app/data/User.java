package app.data;

import app.data.user.Role;
import app.data.user.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("status")
    private Status status;
}
