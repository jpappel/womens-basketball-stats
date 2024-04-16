package com.mu_bball_stats;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class WebBrowser extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a WebView component
        WebView webView = new WebView();

        // Load the URL served by your Javalin server
        webView.getEngine().load("http://localhost:7070");

        // Create a Scene and add the WebView to it
        Scene scene = new Scene(webView, 800, 600);

        // set application icon
        // TODO: update with correct icon
        // FIXME: setting taskbar icon is platorm dependent
        Image icon = new Image("https://yt3.ggpht.com/a/AATXAJynf_UM8nuvZXwz9I5GpF9GUd10r4IqIZY8qw=s900-c-k-c0xffffffff-no-rj-mo");
        primaryStage.getIcons().add(icon);

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
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
