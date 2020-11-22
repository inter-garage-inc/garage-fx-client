package app.controller;

import app.controller.component.MainMenuController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

@RouteMapping(title = "Buscar Cliente Mensalista")
public class CustomerFindController {

    @FXML
    private MainMenuController menuController;

    @FXML
    private ImageView findLoading;

    @FXML
    private Button buttonSearch;

    public void initialize() {
        menuController.btnMonthly.getStyleClass().add("button-menu-selected");
    }
    
    public void handleOnActionButtonButtonSearch() {
        Router.goTo(MonthlyFoundController.class,true);
    }

}
