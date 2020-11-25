package app.controller;

import app.client.ConnectionFailureException;
import app.controller.popup.PopUpRegisterSuccessfulController;
import app.data.Catalog;
import app.data.catalog.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogService;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

@RouteMapping(title = "Cadastro de Serviços")
public class ServiceRegistrationController {


    public TextField fieldService;
    public TextField fieldPrice;
    public ComboBox<Status> cbStatus;
    public Button btnSave;

    public void initialize() {
        cbStatus.getItems().addAll(Status.values());
    }

    public void handleOnActionButtonBtnSave() {
        var catalog = Catalog.builder()
                .description(fieldService.getText())
                .price(new BigDecimal(fieldPrice.getText()))
                .status(cbStatus.getValue())
                .build();
        try {
            CatalogService service = new CatalogService();
            var response = service.CatalogSave(catalog);
            if(response){
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 1);
            } else {
                System.out.println("Não foi possível realizar o cadastro!!");
            }
            Router.goTo(ServiceManagementController.class);
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }
    }
}