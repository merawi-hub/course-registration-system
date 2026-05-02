package com.university.crs;

import com.university.crs.db.DatabaseInitializer;
import com.university.crs.gui.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX application entry point.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        DatabaseInitializer.initialize();
        new LoginScreen(primaryStage).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
