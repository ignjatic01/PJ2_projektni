package org.etfbl.pj2.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class OstalaVozila extends Application {

    private static ObservableList<String> items;
    private static ListView<String> listView;
    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("OstalaVozila");
        primaryStage.setX(670);
        primaryStage.setY(190);

        items = FXCollections.observableArrayList("Item1");
        listView = new ListView<>(items);

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        layout.getChildren().addAll(listView);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static ObservableList<String> getItems() {
        return items;
    }

    public static void setItems(ObservableList<String> items) {
        OstalaVozila.items = items;
    }

    public static ListView<String> getListView() {
        return listView;
    }

    public static void setListView(ObservableList<String> items) {
        Platform.runLater(() -> {
            OstalaVozila.listView.setItems(items);
        });
    }
}
