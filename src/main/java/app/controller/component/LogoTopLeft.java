package app.controller.component;

import app.controller.HomeController;
import app.router.Router;
import javafx.scene.input.MouseEvent;

public class LogoTopLeft {

    public void handleOnMouseClickedLogoImage(MouseEvent mouseEvent) {
        Router.goTo(HomeController.class);
    }
}
