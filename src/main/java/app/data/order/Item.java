package app.data.order;

import app.data.Catalog;
import app.data.DataBase;
import app.data.Order;
import app.data.Parking;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Item> listOf(List<Catalog> catalogs) {
        return catalogs
                .stream()
                .map(Item::of)
                .collect(Collectors.toList());
    }

    public static Item of(Catalog catalog) {
        return Item
                .builder()
                .catalog(catalog)
                .build();
    }
}
