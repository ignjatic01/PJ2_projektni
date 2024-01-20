package org.etfbl.pj2.gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.etfbl.pj2.util.Reporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Kaznjeni extends Application {

    private static ObservableList<String> items;
    private static ListView<String> listView;
    private static HashMap<String, List<String>> kaznjenaVozila;
    @Override
    public void start(Stage primaryStage)
    {
        TreeItem<String> rootItem = new TreeItem<>("Kaznjeni subjekti:");
        rootItem.setExpanded(true);
        initialize();

        // Dodavanje podlisti
        for(String vozilo : kaznjenaVozila.keySet())
        {
            TreeItem<String> sublist = new TreeItem<>(vozilo);
            sublist.setGraphic(new StackPane());
            if(vozilo.contains("(Problem s putnicima)"))
            {
                sublist.getGraphic().setStyle("-fx-background-color: yellow; -fx-padding: 5px;");
            }
            else
            {
                sublist.getGraphic().setStyle("-fx-background-color: red; -fx-padding: 5px;");
            }

            List<String> podaciVozila = kaznjenaVozila.get(vozilo);
            for (String podatak : podaciVozila)
            {
                TreeItem<String> podlista = new TreeItem<>(podatak);
                sublist.getChildren().add(podlista);
            }

            rootItem.getChildren().add(sublist);
        }
//        TreeItem<String> sublist1 = new TreeItem<>("Sublist 1");
//        sublist1.getChildren().addAll(new TreeItem<>("Podatak 1"), new TreeItem<>("Podatak 2"));
//        sublist1.setGraphic(new StackPane());
//        sublist1.getGraphic().setStyle("-fx-background-color: lightblue; -fx-padding: 5px;");
//
//        TreeItem<String> sublist2 = new TreeItem<>("Sublist 2");
//        sublist2.getChildren().addAll(new TreeItem<>("Podatak 3"), new TreeItem<>("Podatak 4"));
//        sublist2.setGraphic(new StackPane());
//        sublist2.getGraphic().setStyle("-fx-background-color: lightgreen; -fx-padding: 5px;");

//        rootItem.getChildren().addAll(sublist1, sublist2);

        TreeView<String> treeView = new TreeView<>(rootItem);

        primaryStage.setScene(new Scene(treeView, 400, 250));
        primaryStage.setTitle("TreeView Primer");
        primaryStage.show();
    }

    private static void initialize()
    {
        kaznjenaVozila = new HashMap<>();
        List<String> inVozila = Reporter.ucitavanjeNeispravnihVozila();
        for(String vozilo : inVozila)
        {
            String[] elements = vozilo.split("#");
            kaznjenaVozila.put(elements[0] + elements[1] + ": " + elements[2], new ArrayList<>());
        }

        List<String> inPutnici = Reporter.ucitavanjeNeispravnihPutnika();
        for (String putnik : inPutnici)
        {
            String[] elements = putnik.split("#");
            String voziloKljuc = "Vozilo" + elements[1] + "(Problem s putnicima)";
            boolean postoji = false;

            for (String v : kaznjenaVozila.keySet()) {
                if (v.startsWith("LicnoVozilo" + elements[1]) || v.startsWith("Autobus" + elements[1]) || v.startsWith("Kamion" + elements[1]) || v.startsWith("Vozilo" + elements[1])) {
                    kaznjenaVozila.get(v).add(elements[2] + " " + elements[3] + " " + elements[4]);
                    postoji = true;
                }
            }

            if (!postoji) {
                ArrayList<String> putnici = new ArrayList<>();
                putnici.add(elements[2] + " " + elements[3] + " " + elements[4]);
                kaznjenaVozila.put(voziloKljuc, putnici);
            }
        }

    }
}
