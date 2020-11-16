package app.data.order;

import app.data.Catalog;
import app.data.DataBase;
import app.data.Order;
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

    private Integer quantity;

    private BigDecimal amount;

    private Order order;

    private Catalog catalog;
}
