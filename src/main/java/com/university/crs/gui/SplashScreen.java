package com.university.crs.gui;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreen {

    private final Stage stage;
    private final Runnable onComplete;

    public SplashScreen(Stage stage, Runnable onComplete) {
        this.stage = stage;
        this.onComplete = onComplete;
    }

    public void show() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: " + ColorScheme.GRADIENT_SIDEBAR + ";");

        VBox content = new VBox(35);
        content.setAlignment(Pos.CENTER);

        // Graduation cap icon (image or drawn)
        StackPane icon = createGraduationCapIcon();
        
        // Add subtle animation to icon
        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(0.9), icon);
        scaleIn.setFromX(0.8);
        scaleIn.setFromY(0.8);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);
        scaleIn.play();

        // Title with Poppins font
        Label title = new Label("COURSE REGISTRATION");
        title.setFont(FontLoader.getPoppinsBold(38));
        title.setTextFill(ColorScheme.WHITE);
        title.setStyle("-fx-letter-spacing: 2px;");
        
        Label titleLine2 = new Label("SYSTEM");
        titleLine2.setFont(FontLoader.getPoppinsBold(38));
        titleLine2.setTextFill(ColorScheme.WHITE);
        titleLine2.setStyle("-fx-letter-spacing: 2px;");
        
        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(title, titleLine2);

        // Subtitle with Inter font
        Label subtitle = new Label("Smart way to register your future");
        subtitle.setFont(FontLoader.getInter(17));
        subtitle.setTextFill(ColorScheme.LIGHT_BLUE);
        subtitle.setStyle("-fx-letter-spacing: 0.5px;");

        // Loading spinner
        StackPane loadingContainer = new StackPane();
        Arc spinner = createSpinner();
        Label loadingText = new Label("Loading...");
        loadingText.setFont(FontLoader.getInter(15));
        loadingText.setTextFill(ColorScheme.LIGHT_BLUE);
        loadingText.setStyle("-fx-letter-spacing: 1px;");
        
        VBox loadingBox = new VBox(18);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.getChildren().addAll(spinner, loadingText);
        loadingContainer.getChildren().add(loadingBox);

        content.getChildren().addAll(icon, titleBox, subtitle, loadingContainer);
        root.getChildren().add(content);

        Scene scene = new Scene(root, 750, 650);
        stage.setScene(scene);
        stage.setTitle("Course Registration System");
        stage.setResizable(false);
        stage.show();

        // Animate spinner
        RotateTransition rotate = new RotateTransition(Duration.seconds(1.2), spinner);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.play();

        // Fade out and transition to login after 3 seconds
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.6), root);
        fadeOut.setDelay(Duration.seconds(2.5));
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            rotate.stop();
            onComplete.run();
        });
        fadeOut.play();
    }

    private StackPane createGraduationCapIcon() {
        StackPane iconContainer = new StackPane();
        
        // Try to load image first
        ImageView imageView = ImageLoader.loadImage("graduation-cap.png", 120, 120);
        
        if (imageView != null) {
            // Image loaded successfully
            iconContainer.getChildren().add(imageView);
        } else {
            // Fallback to drawn icon
            iconContainer.getChildren().addAll(createDrawnGraduationCap());
        }
        
        return iconContainer;
    }
    
    private javafx.scene.Node[] createDrawnGraduationCap() {
        // Graduation cap board (square/mortarboard)
        Polygon capBoard = new Polygon();
        capBoard.getPoints().addAll(
            0.0, -15.0,     // top point
            70.0, 0.0,      // right point
            0.0, 15.0,      // bottom point
            -70.0, 0.0      // left point
        );
        capBoard.setFill(ColorScheme.WHITE);
        capBoard.setStroke(ColorScheme.LIGHT_BLUE);
        capBoard.setStrokeWidth(3);
        
        // Cap base (cylinder/button on top)
        Ellipse capBase = new Ellipse(0, 0, 25, 8);
        capBase.setFill(ColorScheme.WHITE);
        capBase.setStroke(ColorScheme.LIGHT_BLUE);
        capBase.setStrokeWidth(2.5);
        
        // Tassel string
        Line tasselString = new Line(35, 0, 50, 25);
        tasselString.setStroke(ColorScheme.WHITE);
        tasselString.setStrokeWidth(2.5);
        tasselString.setStrokeLineCap(StrokeLineCap.ROUND);
        
        // Tassel top knot
        Circle tasselKnot = new Circle(50, 25, 5);
        tasselKnot.setFill(ColorScheme.WHITE);
        tasselKnot.setStroke(ColorScheme.LIGHT_BLUE);
        tasselKnot.setStrokeWidth(2);
        
        // Tassel threads (multiple lines)
        Line thread1 = new Line(50, 30, 48, 42);
        thread1.setStroke(ColorScheme.WHITE);
        thread1.setStrokeWidth(2);
        thread1.setStrokeLineCap(StrokeLineCap.ROUND);
        
        Line thread2 = new Line(50, 30, 52, 42);
        thread2.setStroke(ColorScheme.WHITE);
        thread2.setStrokeWidth(2);
        thread2.setStrokeLineCap(StrokeLineCap.ROUND);
        
        Line thread3 = new Line(50, 30, 50, 44);
        thread3.setStroke(ColorScheme.WHITE);
        thread3.setStrokeWidth(2);
        thread3.setStrokeLineCap(StrokeLineCap.ROUND);
        
        // Add shadow effect for depth
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.4));
        shadow.setRadius(20);
        shadow.setOffsetY(8);
        capBoard.setEffect(shadow);
        
        return new javafx.scene.Node[] {
            capBoard, 
            capBase, 
            tasselString, 
            tasselKnot,
            thread1,
            thread2,
            thread3
        };
    }

    private Arc createSpinner() {
        Arc arc = new Arc();
        arc.setCenterX(0);
        arc.setCenterY(0);
        arc.setRadiusX(22);
        arc.setRadiusY(22);
        arc.setStartAngle(0);
        arc.setLength(280);
        arc.setType(ArcType.OPEN);
        arc.setFill(null);
        arc.setStroke(ColorScheme.WHITE);
        arc.setStrokeWidth(3.5);
        arc.setStrokeLineCap(StrokeLineCap.ROUND);
        return arc;
    }
}
