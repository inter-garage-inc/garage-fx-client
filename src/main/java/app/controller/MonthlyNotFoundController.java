package app.controller;


import app.router.RouteMapping;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@RouteMapping
public class MonthlyNotFoundController extends ApplicationController {

    @FXML
    private Button btnNewCadaster;

    /**
     * Método para abrir a tela de cadastro
     * @param e
     */
    public void newCadaster(Event e) {

    }
}
