package app.controller.popup;
import app.controller.AlterDeletUserController;
import app.router.RouteMapping;
import app.router.Router;
import javafx.scene.control.Button;
@RouteMapping(title = "Confirmação de exclusão", popup = true)
public class PopUpConfirmDeleteUserController {
    public Button btnDelete;
    public Button btnClose;

    public Integer handleOnActionButtonBtnDelete() {
        return 1;
    }

    public Integer handleOnActionButtonBtnClose() {
        return 1;
    }
}
