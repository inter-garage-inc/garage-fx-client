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

    private List<ParkingSpace> parkingSpaces;

    private ParkingSpacesService parkingSpacesService;

    public ParkingSpacesMapController() {
        parkingSpacesService = new ParkingSpacesService();
    }

    @SneakyThrows
    public void initialize() {
        menuController.btnParkingSpacesMap.getStyleClass().add("button-menu-selected");

        loadMap();
    }

    private void loadMap() {
        try {
            parkingSpaces = parkingSpacesService.index();
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }

        var maxColumn = parkingSpaces.stream().map(ParkingSpace::getColumnPosition).max(Integer::compareTo).orElse(0);
        var maxRow = parkingSpaces.stream().map(ParkingSpace::getRowPosition).max(Integer::compareTo).orElse(0);

        if(gridPaneMap.getColumnCount() < maxColumn) {
            gridPaneMap.getColumnConstraints().removeAll();
            for (int i = 0; i < maxColumn; i++) {
                gridPaneMap.getColumnConstraints().add(new ColumnConstraints(50.0));
            }
        }

        if(gridPaneMap.getRowCount() < maxRow) {
            gridPaneMap.getRowConstraints().removeAll();
            for (int i = 0; i < maxRow; i++) {
                gridPaneMap.getRowConstraints().add(new RowConstraints(50.0));
            }
        }

        gridPaneMap.getChildren().clear();

        parkingSpaces.forEach(parkingSpace -> {
            var label = new Label(parkingSpace.getCode());
            label.getStyleClass().addAll("parkingSpace", parkingSpace.getStatus().name());
            if(parkingSpace.getStatus() != SpaceStatus.OCCUPIED) {
                MenuItem menuItemEnableDisable;
                if(parkingSpace.getStatus() == SpaceStatus.DISABLED) {
                    menuItemEnableDisable = new MenuItem("Ativar");
                    menuItemEnableDisable.setOnAction(event -> this.handleSetEnable(parkingSpace));
                } else {
                    menuItemEnableDisable = new MenuItem("Desativar");
                    menuItemEnableDisable.setOnAction(event -> this.handleSetDisable(parkingSpace));
                }
                var menuItemDelete = new MenuItem("Remover");
                menuItemDelete.setOnAction(event -> this.handleDelete(parkingSpace));
                label.setContextMenu(new ContextMenu(menuItemEnableDisable, menuItemDelete));
            }
            gridPaneMap.add(label, parkingSpace.getColumnPosition(), parkingSpace.getRowPosition());
        });
    }

    public void handleSetDisable(ParkingSpace parkingSpace) {
        var ps = ParkingSpace
                .builder()
                .code(parkingSpace.getCode())
                .status(SpaceStatus.DISABLED)
                .build();
        try {
            parkingSpacesService.update(parkingSpace.getId(), ps);
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
            parkingSpacesService.update(parkingSpace.getId(), ps);
            loadMap();
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }
    }

    public void handleDelete(ParkingSpace parkingSpace) {
        try {
            parkingSpacesService.delete(parkingSpace.getId());
            loadMap();
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }
    }
}
