package app.controller.component;

import app.controller.*;
import app.controller.popup.PlateNotFoundController;
import app.data.user.Role;
import app.router.Router;
import app.service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.sql.Struct;

public class MainMenuController {
    public Button buttonCheckIn;
    public Button buttonCheckout;
    public Button buttonMonthly;
    public Button buttonOpenAccounts;
    public Button buttonVacancyMap;

    public void initialize() {
        var user = AuthenticationService.claimUser();
        if(user.getRole() == Role.EMPLOYEE) {
            buttonVacancyMap.setVisible(false); //TODO Test Only
            buttonOpenAccounts.setVisible(false); //TODO Test Only
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
        Router.showPopUp(PlateNotFoundController.class); //TODO Test Only
    }
}
