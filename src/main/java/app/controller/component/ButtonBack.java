package app.controller.component;

import app.router.Router;
import javafx.event.ActionEvent;

public class ButtonBack {
    public void handleOnActionButtonBack(ActionEvent actionEvent) {
        Router.back();
    }
}
