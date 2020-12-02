package app.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderItem {
    private String licensePlate;
    private String description;
}
