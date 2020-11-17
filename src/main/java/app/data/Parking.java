package app.data;

import app.data.parking.ParkingSpace;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Parking extends DataBase {
    @JsonProperty("check_in_at")
    private LocalDateTime checkInAt;

    @JsonProperty("checkout_at")
    private LocalDateTime checkoutAt;

    @JsonProperty("parking_space")
    private ParkingSpace parkingSpace;
}
