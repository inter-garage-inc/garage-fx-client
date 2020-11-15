package app.controller.component;

import app.controller.CheckInController;
import app.controller.CheckoutController;
import app.controller.MonthlyCustomerController;
import app.controller.OrdersOpenController;
import app.controller.popup.PlateNotFoundController;
import app.data.user.Role;
import app.router.Router;
import app.service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

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
        Router.goTo(CheckInController.class);
    }

    public void handleOnActionButtonCheckout(ActionEvent actionEvent) {
        Router.goTo(CheckoutController.class);
    }

    public void handleOnActionButtonMonthly(ActionEvent actionEvent) {
        Router.goTo(MonthlyCustomerController.class);
    }

    public void handleOnActionButtonOrdersOpen(ActionEvent actionEvent) {
        Router.goTo(OrdersOpenController.class);
    }

    public void handleOnActionButtonVacancyMap(ActionEvent actionEvent) {
        Router.showPopUp(PlateNotFoundController.class); //TODO Test Only
    }
}
