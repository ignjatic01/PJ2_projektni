package org.etfbl.pj2.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Log extends Application {

//    public static void main(String[] args) {
//        launch(args);
//    }

    private static TextArea logTextArea;
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Log GUI");
        primaryStage.setX(320);
        primaryStage.setY(190);

        logTextArea = new TextArea();
        logTextArea.setEditable(false);

        logTextArea.setMinHeight(300);
        logTextArea.setMinWidth(400);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(logTextArea);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void logMessage(String message)
    {
        Platform.runLater(() -> logTextArea.appendText(message + "\n"));
    }

}
