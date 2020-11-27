package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.CheckInConfirmController;
import app.controller.popup.PopUpServerCloseController;
import app.data.catalog.CatalogType;
import app.data.order.Item;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import app.service.ItemService;

import java.util.ArrayList;

@RouteMapping
public class CheckInConfirmationController {

    public Button btnAlter;
    public Button btnSave;
    public Button btnPay;
    public Label lblVehicle;
    public Label lblLicensePlate;
    public Label lblServices;
    public AnchorPane anchorPane;
    public ItemService service;

    public CheckInConfirmationController() {
        service = new ItemService();
    }

    public void handleOnActionButtonBtnAlter() {
        Router.goTo(ChangeCheckInController.class, true);
    }

    public void handleOnActionButtonBtnSave() throws ConnectionFailureException {
        ArrayList<Item> response = (ArrayList<Item>) Router.getUserData();

        Item.ItemBuilder items = null;
        for(Item item : response) {
            items = Item.builder()
                    .catalog(item.getCatalog())
                    .description(item.getDescription())
                    .parking(item.getParking())
                    .price(item.getPrice());
        }
        var item = items.build();

        try {
            var response2 = service.itemFindByLicensePlate(item);
            if (response2 != null) {
                if(service.itemsSave(item)) {
                    Router.showPopUp(CheckInConfirmController.class, 2);
                    Router.goTo(HomeController.class);
                }
            }

        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }


    }

    public void handleOnActionButtonBtnPay() throws InterruptedException {
        System.out.println("Carregando tela de Check Out"); //TODO criar um pop up para carregar a tela de check out
        Thread.sleep(1000);
        Router.goTo(CheckOutConfirmationController.class, true);
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        ArrayList<Item> response = (ArrayList<Item>) Router.getUserData();

        Double layoutY = 5.0;
        Double layoutX = 5.0;

        for (Item item : response) {
            if (item.getCatalog().getType().equals(CatalogType.PARKING)) {
                lblLicensePlate.setText(item.getParking().getLicensePlate());
            }
            Label label = new Label();
            label.setText(" ⌂ "+item.getDescription());
            label.getStyleClass().addAll("label");
            label.setLayoutY(layoutY);
            label.setLayoutX(layoutX);
            layoutY += 20;
            anchorPane.getChildren().addAll(label);
        }


        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");
    }

}
