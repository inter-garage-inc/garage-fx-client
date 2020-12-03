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
    public MenuButton btnOpenAccounts;
    public MenuItem btnCustomerMonthly;
    public MenuItem btnCustomerSingle;
    public Button btnParkingSpacesMap;
    public MenuButton btnServicePlans;
    public Button btnUsers;
    public MenuItem btnPlansManagement;
    public MenuItem btnCatalogManagement;

    public void initialize() {
        var user = AuthenticationService.claimUser();
        if(user.getRole() == Role.EMPLOYEE) {
            btnServicePlans.setVisible(false);
            btnUsers.setVisible(false);

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
        Router.goTo(CustomerSearchController.class, true);
    }

    public void handleOnActionButtonBtnCustomerMonthly() {
        Router.goTo(CustomerOrdersOpenController.class, true);
    }

    public void handleOnActionButtonBtnCustomerSingle() {
        Router.goTo(SingleOrdersOpenController.class, true);
    }

    public void handleOnActionButtonParkingSpacesMap(ActionEvent actionEvent) {
        Router.goTo(ParkingSpacesMapController.class, true);
    }

    public void handleOnActionButtonPlansManagement(ActionEvent actionEvent) {
        Router.goTo(PlanManagementController.class, true);
    }

    public void handleOnActionButtonCatalog(ActionEvent actionEvent) {
        Router.goTo(CatalogManagementController.class, true);
    }

    public void handleOnActionButtonUsers(ActionEvent actionEvent) {
        Router.goTo(PeopleManagementController.class, true);
    }
}
