package app.data;

import app.data.parking.ParkingSpace;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Parking extends DataBase {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("check_in_at")
    private LocalDateTime checkInAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("checkout_at")
    private LocalDateTime checkoutAt;

    @JsonProperty("parking_space")
    private ParkingSpace parkingSpace;

}
