package app.data;

import app.data.user.Role;
import app.data.user.Status;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends DataBase {
    private String name;

    private String username;

    private String password;

    private Role role;

    private Status status;
}
