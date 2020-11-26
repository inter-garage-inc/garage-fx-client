package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.parking.ParkingSpace;
import app.data.parking.SpaceStatus;
import app.router.RouteMapping;
import app.service.ParkingSpacesService;
import app.util.Alphabetic;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import lombok.SneakyThrows;

import java.util.*;

@RouteMapping(title = "Mapa de Vagas")
public class ParkingSpacesMapController {
    @FXML
    private MainMenuController menuController;

    @FXML
    private GridPane gridPaneMap;

    private ParkingSpacesService service;

    private List<ParkingSpace> parkingSpaces;

    public ParkingSpacesMapController() {
        service = new ParkingSpacesService();

        /* insert parking spaces
        for(int c = 0; c <= 5; c++) {
            for(int r = 0; r <= 10; r++) {
                try {
                    service.save(ParkingSpace.builder().code(Alphabetic.parseInt(c) + r).status(SpaceStatus.VACANT).build());
                } catch (ConnectionFailureException e) {
                    e.printStackTrace();
                }
            }
        }
        */
    }

    @SneakyThrows
    public void initialize() {
        menuController.btnParkingSpacesMap.getStyleClass().add("button-menu-selected");

        loadMap();
    }

    private void loadMap() {
        try {
            parkingSpaces = service.index();
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }

        parkingSpaces.forEach(ps -> {
            var labelCode = new Label(ps.getCode());
            labelCode.getStyleClass().addAll("parkingSpace", ps.getStatus().name());

            if(ps.getStatus() != SpaceStatus.OCCUPIED) {
                var menuItemEnableDisable = new MenuItem(
                        ps.getStatus() == SpaceStatus.DISABLED
                            ? "Ativar"
                            : "Desativar"
                );
                menuItemEnableDisable.setOnAction(
                        ps.getStatus() == SpaceStatus.DISABLED
                            ? event -> this.handleSetEnable(ps)
                            : event -> this.handleSetDisable(ps)
                );
                var menuItemDelete = new MenuItem("Remover");
                menuItemDelete.setOnAction(event -> this.handleDelete(ps));
                labelCode.setContextMenu(new ContextMenu(menuItemEnableDisable, menuItemDelete));
            }
            gridPaneMap.add(labelCode, ps.getColumnPosition(), ps.getRowPosition());
        });
    }

    public void handleSetDisable(ParkingSpace parkingSpace) {
        var ps = ParkingSpace
                .builder()
                .code(parkingSpace.getCode())
                .status(SpaceStatus.DISABLED)
                .build();
        try {
            service.update(parkingSpace.getId(), ps);
            loadMap();
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }
    }

    public void handleSetEnable(ParkingSpace parkingSpace) {
        var ps = ParkingSpace
                .builder()
                .code(parkingSpace.getCode())
                .status(SpaceStatus.VACANT)
                .build();
        try {
            service.update(parkingSpace.getId(), ps);
            loadMap();
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }
    }

    public void handleDelete(ParkingSpace parkingSpace) {
        try {
            service.delete(parkingSpace.getId());
            loadMap();
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }
    }
}
