package app.controller;


import app.router.RouteMapping;
import app.service.AuthenticationService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RouteMapping(title = "Página inicial")
public class HomeController {

    @FXML
    private Label labelWelcome;

    @FXML
    private Label labelClock;

    public void initialize() {
        initClock();
        initWelcome();
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            var locale = new Locale("pt", "BR");
            var formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy\nHH:mm:ss").localizedBy(locale);
            labelClock.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void initWelcome() {
        var user = AuthenticationService.claimUser();
        labelWelcome.setText("Bem-vindo " + user.getName());
    }
}
