package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpDeleteSuccessController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Catalog;
import app.data.catalog.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogsService;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

@RouteMapping(title = "Alteração de Serviços")
public class CatalogChangeController {

    public Button btnAlter;
    public Button btnDelete;
    public TextField fieldService;
    public TextField fieldPrice;
    public ComboBox<Status> cbStatus;
    public Catalog catalog;
    public MainMenuController menuController;
    public Label lblMessage;

    public void initialize() {
        menuController.btnCatalogManagement.getStyleClass().add("button-menu-selected");
        catalog = (Catalog) Router.getUserData();
        fieldPrice.setText(catalog.getPrice().toString());
        fieldService.setText(catalog.getDescription());

        cbStatus.getItems().addAll(Status.values());
        cbStatus.setValue(catalog.getStatus());
    }

    public void handleOnActionButtonBtnAlter() {
        try {
            Boolean priceNull = fieldPrice.getText().isBlank();
            Boolean serviceNull = fieldService.getText().isBlank();
            Boolean cbStatusNull = cbStatus.getValue() == null;

            if(cbStatusNull || priceNull || serviceNull) {
                lblMessage.setText("Todos os campos precisam ser preenchidos");
                return;
            }


            var catalog2 = Catalog.builder()
                    .description(fieldService.getText())
                    .price(new BigDecimal(fieldPrice.getText()))
                    .status(cbStatus.getValue())
                    .build();

            CatalogsService service = new CatalogsService();
            service.CatalogUpdate(catalog2, catalog.getId());

            Router.goTo(CatalogManagementController.class);
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, true);
        }
    }

    public void handleOnActionButtonBtnDelete() { // TODO Verificar se o serviço esta cadastrado a um plano
        try {
            CatalogsService service = new CatalogsService();
            service.CatalogDelete(catalog.getId());
            Router.showPopUp(PopUpDeleteSuccessController.class);
            Router.goTo(CatalogManagementController.class);
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }

    }


}
