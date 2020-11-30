package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.Catalog;
import app.data.Order;
import app.data.Parking;
import app.data.order.Item;
import app.data.order.Status;
import app.data.parking.SpaceStatus;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogService;
import app.service.OrderService;
import app.service.ParkingSpacesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RouteMapping(title = "Check in")
public class CheckInController {

    public Button btnOk;
    public TextField txtLicensePlate;
    public Label lblMessage;
    @FXML private MainMenuController menuController;
    public AnchorPane anchorPane;
    public CheckBox catalogCheckBox;
    public CatalogService catalogService;
    public OrderService orderService;
    public ParkingSpacesService parkingSpacesService;
    public ArrayList<Catalog> catalogs;

    public CheckInController() {
        catalogService = new CatalogService();
        orderService = new OrderService();
        parkingSpacesService = new ParkingSpacesService();

        catalogs = new ArrayList<>();
    }

    public void handleOnActionButtonOk(ActionEvent actionEvent) throws ConnectionFailureException {
        ArrayList<Item> items = new ArrayList<>();

        Boolean nullLicensePlate = txtLicensePlate.getText() == null || txtLicensePlate.getText().trim().isEmpty();
        Boolean nullServices = catalogs.isEmpty();
        BigDecimal price = BigDecimal.valueOf(0.0);
        var response = orderService.ordersFindByLicensePlate(txtLicensePlate.getText());
        var parkingSpace = parkingSpacesService.findAvailable();

        if(response) {
           lblMessage.setText("Placa com check in em aberto");
           return;
        }

        if(parkingSpacesService == null) {
            lblMessage.setText("Não há vagas no momento");
            return;
        }
        
        var response = orderService.ordersFindByLicensePlate(txtLicensePlate.getText());
        var response1 = parkingSpacesService.findAvailable();

        if(response) {
           lblMessage.setText("Placa com check in em aberto");
           return;
        }

        if(parkingSpacesService == null) {
            lblMessage.setText("Não há vagas no momento");
            return;
        }

        if(!nullLicensePlate && !nullServices) {
            parkingSpace.setStatus(SpaceStatus.OCCUPIED);
                for (Catalog catalog : catalogs) {
                    price = price.add(catalog.getPrice());
                    Item item = Item.builder()
                            .catalog(catalog)
                            .description(catalog.getDescription())
                            .price(catalog.getPrice())
                            .parking(Parking.builder()
                                    .parkingSpace(parkingSpace)
                                    .checkInAt(LocalDateTime.now())
                                    .build())
                            .build();
                    items.add(item);
                }
                Order order = Order.builder()
                        .items(items)
                        .totalAmount(price)
                        .licensePlate(txtLicensePlate.getText())
                        .status(Status.OPEN)
                        .build();
                Router.goTo(CheckInConfirmationController.class, order);
        } else {
            lblMessage.setText("Campos vazios");
        }
    }

    public void initialize() throws ConnectionFailureException {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");

        Double layoutY = 10.0;
        for (Catalog catalog : catalogService.CatalogFindAll()) {
            if (!catalog.getStatus().equals(app.data.catalog.Status.UNAVAILABLE)) {
                var response = catalog.getDescription();
                catalogCheckBox = new CheckBox();
                catalogCheckBox.setText(response);
                catalogCheckBox.selectedProperty().addListener((observable, wasSelected, isSelected) ->{
                    if(isSelected) {
                        catalogs.add(catalog);
                    } else {
                        catalogs.remove(catalog);
                    }
                });
                catalogCheckBox.setLayoutY(layoutY);
                catalogCheckBox.setLayoutX(10.0);
                anchorPane.getChildren().addAll(catalogCheckBox);
                layoutY+=20;
            }
        }
    }

}
