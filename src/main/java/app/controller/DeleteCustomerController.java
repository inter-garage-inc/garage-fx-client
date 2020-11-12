package app.controller;

import app.router.RouteMapping;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@RouteMapping(title = "Exclusão de cliente")
public class DeleteCustomerController extends ApplicationController{
    @FXML
    private TextField labeltext;
}
