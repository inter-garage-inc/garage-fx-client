package app.controller.popup;

import app.client.ConnectionFailureException;
import app.controller.ParkingSpacesMapController;
import app.data.parking.ParkingSpace;
import app.data.parking.SpaceStatus;
import app.router.RouteMapping;
import app.router.Router;
import app.service.ParkingSpacesService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@RouteMapping(title = "Nova Vaga", popup = true)
public class PopUpParkingSpaceRegistration {
    @FXML
    private TextField fieldCode;

    @FXML
    private Label labelMessage;

    private ParkingSpacesService service;

    public PopUpParkingSpaceRegistration() {
        service = new ParkingSpacesService();
    }

    @FXML
    private void initialize() {
        fieldCode.textProperty().addListener((observable, oldValue, newValue) -> fieldCode.setText(newValue
                .toUpperCase().replaceAll(".*?([A-Z]+[0-9]*+).*$|.()", "$1")));
    }

    @FXML
    private void handleSpaceRegistration(ActionEvent actionEvent) {
        try {
            var ps = ParkingSpace.builder()
                    .code(fieldCode.getText())
                    .status(SpaceStatus.VACANT)
                    .build();

            if(service.save(ps)) {
                Router.goTo(ParkingSpacesMapController.class, null);
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 3);
            } else {
                labelMessage.setText("A vaga já está cadastrada.\nVerifique o código inserido.");
            }
        } catch (IllegalArgumentException exception) {
            labelMessage.setText("Código inválido");
        } catch (ConnectionFailureException exception) {
            exception.printStackTrace();
        }
    }
}
