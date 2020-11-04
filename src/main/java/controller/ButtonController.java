package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class ButtonController {

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
}
