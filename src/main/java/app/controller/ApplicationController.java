package app.controller;

import app.router.Router;
import app.router.RouteMapping;
import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

@RouteMapping
public abstract class ApplicationController {

    @FXML
    protected Button btnCheckIn;

    @FXML
    protected Button btnCheckout;

    @FXML
    protected Button btnMonthly;

    @FXML
    protected Button btnOpenAccounts;

    @FXML
    protected Button btnVanacyMap;

    @FXML
    protected Button btnBack;

    @FXML
    protected Button btnClose;

    @FXML
    protected Button btnSrc;

    /**
     * método para abrir a tela de checkin
     * @param e
     */

    public void handleHome(Event e) {
        Router.goTo(HomeController.class);
    }

    public void checkIn(Event e) {
        Router.goTo(CheckInController.class, true);
    }

    /**
     * Método para abrir a tela de checkout
     * @param e
     */
    public void checkout(Event e) {

    }

    /**
     * Método para abrir a tela de mensalistas
     * @param e
     */
    public void monthly(Event e) {
        Router.goTo(MonthlyCustomerController.class, true);
    }

    /**
     * Método para abrir a tela de contas abertas
     * @param e
     */
    public void openAccounts(Event e) {
        Router.goTo(OrdersOpenController.class, true);
    }

    /**
     * Método para abrir a tela de mapa de vagas
     * @param e
     */
    public void vanacyMap(Event e) {
        Router.goTo(RegisterCustomerController.class);
    }

    /**
     * Método para voltar a página anterior
     * @param e
     */
    public void back(Event e){
        Router.back();
    }

    /**
     * Método para fechar a notificação
     * @param event
     */
    @FXML
    public void closeForm(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
