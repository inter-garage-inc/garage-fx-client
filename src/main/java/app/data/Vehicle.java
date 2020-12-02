package app.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Vehicle extends DataBase {
    @JsonProperty("license_plate")
    private String licencePlate;

    private Plan plan;
}
