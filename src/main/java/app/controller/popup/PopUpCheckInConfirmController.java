package app.controller.popup;

import app.data.Order;
import app.data.catalog.CatalogType;
import app.data.order.Item;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Checkin", popup = true)
public class PopUpCheckInConfirmController {

    @FXML
    private Label lblParkingSpace;

    public Order created;

    /**
     * The initialize method receive the data the {@link Order} from {@link app.controller.CheckInConfirmationController} using {@link Router}
     */
    public void initialize(){
        created = (Order) Router.getUserData();
        setLblParkingSpace();
    }

    /**
     * This method confirm the {@link app.controller.CheckInController} and show the {@link app.data.parking.ParkingSpace}
     */
    public void setLblParkingSpace() {
        String parkingSpace = "";
        for(Item item : created.getItems()) {
            if(item.getCatalog().getType() == CatalogType.PARKING) {
                parkingSpace = item.getParking().getParkingSpace().getCode();
                lblParkingSpace.setText("Vaga: " + parkingSpace);
            }
        }

    }
}
