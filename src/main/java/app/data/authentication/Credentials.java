package app.data.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Credentials {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}