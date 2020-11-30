package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.Catalog;
import app.data.Order;
import app.data.Parking;
import app.data.order.Item;
import app.data.order.PaymentMethod;
import app.data.order.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.AuthenticationService;
import app.service.CatalogService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.util.ArrayList;

@RouteMapping(title = "Check in")
public class CheckInController {

    public Button btnOk;
    public TextField txtLicensePlate;
    public Label lblMessage;
    @FXML private MainMenuController menuController;
    public AnchorPane anchorPane;
    public CheckBox catalogCheckBox;
    public CatalogService service;
    public ArrayList<Catalog> catalogs;

    public CheckInController() {
        service = new CatalogService();
        catalogs = new ArrayList<>();
    }

    public void handleOnActionButtonOk(ActionEvent actionEvent) {
        ArrayList<Item> items = new ArrayList<>();

        Boolean nullLicensePlate = txtLicensePlate.getText() == null || txtLicensePlate.getText().trim().isEmpty();
        Boolean nullServices = catalogs.isEmpty();
        BigDecimal price = BigDecimal.valueOf(0.0);

        if(!nullLicensePlate && !nullServices) {
            for (Catalog catalog : catalogs) {
                price = price.add(catalog.getPrice());
                Item item = Item.builder()
                        .catalog(catalog)
                        .description(catalog.getDescription())
                        .price(catalog.getPrice())
                        .parking(Parking.builder()
                                .licensePlate(txtLicensePlate.getText())
                                .checkInAt(catalog.getCreatedAt())
                                .build())
                        .build();
                items.add(item);
            }
            Order order = Order.builder()
                    .items(items)
                    .paymentMethod(PaymentMethod.CARD)
                    .totalAmount(price)
                    .status(Status.PAID)
                    .user(AuthenticationService.claimUser())
                    .build();
            Router.goTo(CheckInConfirmationController.class, order);
        } else {
            lblMessage.setText("Campos vazios");
        }
    }

    public void initialize() throws ConnectionFailureException {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");

        Double layoutY = 10.0;
        for (Catalog catalog : service.CatalogFindAll()) {
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
