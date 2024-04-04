package com.mu_bball_stats;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebBrowser extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a WebView component
        WebView webView = new WebView();

        // Load the URL served by your Javalin server
        webView.getEngine().load("http://localhost:7070");

        // Create a Scene and add the WebView to it
        Scene scene = new Scene(webView, 800, 600);

        // Set the Scene on the Stage
        primaryStage.setScene(scene);

        // Set the title of the Stage
        primaryStage.setTitle("Women's Basketball Stats");

        // Set an event handler to handle window close event
        primaryStage.setOnCloseRequest(event -> {
            // Shutdown the JavaFX platform
            Platform.exit();
            // Shutdown the Java Virtual Machine (JVM)
            System.exit(0);
        });

        // Show the Stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
