package app.controller.popup;

import app.data.Order;
import app.data.catalog.CatalogType;
import app.data.order.Item;
import app.data.parking.ParkingSpace;
import app.router.RouteMapping;
import app.router.Router;
import javafx.scene.control.Label;

@RouteMapping(title = "Checkin", popup = true)
public class PopUpCheckInConfirmController {

    public Order created;
    public Label lblParkingSpace;

    public void initialize(){
        created = (Order) Router.getUserData();
        System.out.println(created.getItems().get(0).getParking().getParkingSpace().getCode());
        setLblParkingSpace();
    }

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
