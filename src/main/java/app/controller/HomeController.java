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

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Página inicial")
public class HomeController {

    @FXML
    private Label labelWelcome;

    @FXML
    private Label labelClock;

    /**
     * The initialize method start the methods
     * @see HomeController#initClock
     * @see HomeController#initWelcome
     * */
    public void initialize() {
        initClock();
        initWelcome();
    }

    /**
     * This method create a clock in {@link HomeController}.
     */
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            var locale = new Locale("pt", "BR");
            var formatter = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy\nHH:mm:ss").localizedBy(locale);
            labelClock.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /**
     * This method catch {@link app.data.User} and add a welcome message in {@link HomeController}.
     */
    private void initWelcome() {
        var user = AuthenticationService.claimUser();
        labelWelcome.setText("Bem-vindo " + user.getName());
    }
}
