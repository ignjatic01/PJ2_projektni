package org.etfbl.pj2.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.etfbl.pj2.putnik.Putnik;
import org.etfbl.pj2.vozilo.Vozilo;

public class VoziloView extends Application {

    private Vozilo vozilo;
    private static TextArea putnici;

    public Vozilo getVozilo() {
        return vozilo;
    }

    public void setVozilo(Vozilo vozilo) {
        this.vozilo = vozilo;
    }

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle(vozilo.getClass().getSimpleName() + vozilo.getIdVozilo());

        Label label = new Label(vozilo.getClass().getSimpleName() + vozilo.getIdVozilo());
        StackPane naslov = new StackPane();
        naslov.getChildren().addAll(label);

        StackPane opis = new StackPane();
        Label info = new Label(vozilo.toString());
        opis.getChildren().addAll(info);

        putnici = new TextArea();
        putnici.setEditable(false);
        putnici.setMinHeight(200);
        putnici.setMinWidth(360);
        putnici.setMaxWidth(360);

        StackPane wrapperPutnici = new StackPane();
        wrapperPutnici.setAlignment(Pos.CENTER);
        wrapperPutnici.getChildren().addAll(putnici);

        for(Putnik p : vozilo.getPutnici())
        {
            putnici.appendText(p + "\n");
        }

        VBox layaout = new VBox();
        layaout.setPadding(new Insets(10, 10, 10, 10));
        layaout.setSpacing(10);
        layaout.getChildren().addAll(naslov, opis , wrapperPutnici);

        Scene scene = new Scene(layaout, 500, 320);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
