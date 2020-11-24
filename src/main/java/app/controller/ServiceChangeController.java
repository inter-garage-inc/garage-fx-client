package app.controller;

import app.controller.popup.PopUpDeleteSuccessController;
import app.data.Catalog;
import app.data.catalog.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogService;
import app.client.ConnectionFailureException;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

@RouteMapping(title = "Alteração de Serviços")
public class ServiceChangeController  {

    public Button btnAlter;
    public Button btnDelete;
    public TextField fieldService;
    public TextField fieldPrice;
    public ComboBox<Status> cbStatus;
    public Catalog catalog;

    public void initialize() {
        catalog = (Catalog) Router.getUserData();
        fieldPrice.setText(catalog.getPrice().toString());
        fieldService.setText(catalog.getDescription());

        cbStatus.getItems().addAll(Status.values());
        cbStatus.setValue(catalog.getStatus());
    }

    public void handleOnActionButtonBtnAlter() throws ConnectionFailureException {
        var catalog2 = Catalog.builder()
                .description(fieldService.getText())
                .price(new BigDecimal(fieldPrice.getText()))
                .status(cbStatus.getValue())
                .build();

        CatalogService service = new CatalogService();
        service.CatalogUpdate(catalog2, catalog.getId());

        Router.goTo(ServiceManagementController.class);
    }

    public void handleOnActionButtonBtnDelete() throws ConnectionFailureException { // TODO Verificar se o serviço esta cadastrado a um plano
        var catalog2 = Catalog.builder()
                .description(fieldService.getText())
                .price(new BigDecimal(fieldPrice.getText()))
                .status(cbStatus.getValue())
                .build();

        CatalogService service = new CatalogService();
        service.CatalogDelete(catalog.getId());

        Router.showPopUp(PopUpDeleteSuccessController.class, 1);
        Router.goTo(ServiceManagementController.class);

    }


}
