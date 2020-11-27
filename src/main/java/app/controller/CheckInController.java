package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.Catalog;
import app.data.Order;
import app.data.Parking;
import app.data.catalog.CatalogType;
import app.data.catalog.Status;
import app.data.order.Item;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

        if(!nullLicensePlate && !nullServices) {
            catalogs.forEach(catalog -> {
                var item = Item.builder()
                        .catalog(catalog)
                        .description(catalog.getDescription())
                        .price(catalog.getPrice())
                        .parking(catalog.getType() == CatalogType.PARKING ? Parking.builder()
                                .licensePlate(txtLicensePlate.getText())
                                .build() : null)
                        .build();
                items.add(item);
            });
            Router.goTo(CheckInConfirmationController.class, items);
        } else {
            lblMessage.setText("Campos vazios");
        }
    }

    public void initialize() throws ConnectionFailureException {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");

        Double layoutY = 10.0;
        for (Catalog catalog : service.CatalogFindAll()) {
            if (!catalog.getStatus().equals(Status.UNAVAILABLE)) {
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
