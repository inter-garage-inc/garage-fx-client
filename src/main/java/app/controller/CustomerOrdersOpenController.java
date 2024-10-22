package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping( title = "Contas abertas | Mensalistas")
public class CustomerOrdersOpenController {

    public Button btnSelect;

    public void handleOnActionButtonBtnSelect() {
//        Boolean response = tbView.getSelectionModel().getSelectedItem() != null;
//        if(response) {
//            var catalog = tbView.getSelectionModel().getSelectedItem();
//            Router.goTo(CatalogChangeController.class, catalog, true);
//        }
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnOpenAccounts.getStyleClass().add("button-menu-selected");
    }
}
