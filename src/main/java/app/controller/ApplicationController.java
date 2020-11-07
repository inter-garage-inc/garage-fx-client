package app.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class ApplicationController {

    @FXML
    protected Button btnChekin;

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

    /**
     * método para abrir a tela de checkin
     * @param e
     */
    public void checkin(Event e) {

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

    }

    /**
     * Método para abrir a tela de contas abertas
     * @param e
     */
    public void openAccounts(Event e) {

    }

    /**
     * Método para abrir a tela de mapa de vagas
     * @param e
     */
    public void vanacyMap(Event e) {

    }

    /**
     * Método para voltar a página anterior
     * @param e
     */
    public void back(Event e){

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
