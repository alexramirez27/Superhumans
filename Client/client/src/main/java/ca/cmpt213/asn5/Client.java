package ca.cmpt213.asn5;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import ca.cmpt213.asn5.ui.MainMenu;

/**
 * Class Client. Contains the user interface that will be used for the application.
 * It starts with the main menu scene and contains buttons that allow the user to change scenes.
 * @author Alex Ramirez
 * @version 1.0
 */
public class Client extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        MainMenu.startContent(stage);
    }
}