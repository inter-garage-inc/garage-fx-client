package app.controller;

import app.Router;
import javafx.event.Event;

import java.io.IOException;

public class LoginController {
    public void Entrar(Event e) throws IOException {
        Router.show("home");
    }
}
