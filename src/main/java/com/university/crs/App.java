package com.university.crs;

import com.university.crs.db.DatabaseInitializer;
import com.university.crs.gui.LoginScreen;
import com.university.crs.gui.SplashScreen;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * JavaFX application entry point.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        DatabaseInitializer.initialize();
        
        // Show splash screen first, then login screen
        new SplashScreen(primaryStage, () -> {
            LoginScreen loginScreen = new LoginScreen(primaryStage);
            loginScreen.show();
            
            // Apply global stylesheet
            try {
                String css = getClass().getResource("/styles.css").toExternalForm();
                primaryStage.getScene().getStylesheets().add(css);
            } catch (Exception e) {
                System.err.println("Could not load styles.css: " + e.getMessage());
            }
        }).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
