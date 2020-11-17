package app.controller.component;

import app.controller.*;
import app.data.user.Role;
import app.router.Router;
import app.service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class MainMenuController {
    public Button btnCheckIn;
    public Button btnCheckout;
    public Button btnMonthly;
    public Button btnOpenAccounts;
    public Button btnVacancyMap;
    public MenuButton btnServicePlans;
    public Button btnUsers;
    public MenuItem btnPlansManagement;
    public MenuItem btnCatalogManagement;
    public double y;
    public double x;
    public String text;
    public Button btnSelected;

    public void initialize() {
        var user = AuthenticationService.claimUser();
        if(user.getRole() == Role.EMPLOYEE) {
            btnServicePlans.setVisible(false); //TODO Test Only
            btnUsers.setVisible(false); //TODO Test Only

        } else if(user.getRole() == Role.MANAGER) {

        } else if(user.getRole() == Role.ADMIN) {

        }
    }

    public void handleOnActionButtonCheckIn(ActionEvent actionEvent) {
        Router.goTo(CheckInController.class, true);
    }

    public void handleOnActionButtonCheckout(ActionEvent actionEvent) {
        Router.goTo(CheckoutController.class, true);
    }

    public void handleOnActionButtonMonthly(ActionEvent actionEvent) {
        Router.goTo(MonthlyCustomerController.class, true);
    }

    public void handleOnActionButtonOrdersOpen(ActionEvent actionEvent) {
        Router.goTo(OrdersOpenController.class, true); // TODO Test Only
    }

    public void handleOnActionButtonVacancyMap(ActionEvent actionEvent) {
        //TODO
    }

    public void handleOnActionButtonPlansManagement(ActionEvent actionEvent) {

    }

    public void handleOnActionButtonCatalog(ActionEvent actionEvent) {
        Router.goTo(ServiceManagementController.class, true);
    }

    public void handleOnActionButtonUsers(ActionEvent actionEvent) {

    }
}
