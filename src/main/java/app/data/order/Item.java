package app.data.order;

import app.data.Catalog;
import app.data.DataBase;
import app.data.Order;
import app.data.Parking;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Item extends DataBase {

    private BigDecimal price;

    private String description;

    private Catalog catalog;

    private Parking parking;
}
