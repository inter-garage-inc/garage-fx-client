package app.controller.component;

import app.router.Router;
import javafx.scene.control.Button;

public class ButtonOkController {

    public Button btnOk;

    public void handleOnActionButtonBtnOk() {
        Router.closePopUp();
    }
}
