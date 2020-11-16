package app.data;

import app.data.catalog.Status;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Catalog {
    private String description;

    private BigDecimal price;

    private Status status;
}
