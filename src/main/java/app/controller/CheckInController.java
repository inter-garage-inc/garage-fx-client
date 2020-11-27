package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.Catalog;
import app.data.catalog.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

@RouteMapping(title = "Check in")
public class CheckInController {

    public Button btnOk;
    @FXML private MainMenuController menuController;
    public AnchorPane anchorPane;
    public CheckBox catalogCheckBox;
    public CatalogService service;
    public ArrayList<Catalog> arrayList;

    public CheckInController() {
        service = new CatalogService();
        arrayList = new ArrayList<>();
    }

    public void handleOnActionButtonOk(ActionEvent actionEvent) {
        Router.goTo(CheckInConfirmationController.class, true);
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
                        arrayList.add(catalog);
                    } else {
                        arrayList.remove(catalog);
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
