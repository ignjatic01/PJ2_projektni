package org.etfbl.pj2.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.etfbl.pj2.util.Initializator;
import org.etfbl.pj2.vozilo.Vozilo;

import java.util.concurrent.CountDownLatch;

public class Main extends Application implements Runnable
{
    private static CountDownLatch guiInitializedLatch = new CountDownLatch(1);
    private static Button btn1;
    private static Button btn2;
    private static Button btn3;
    private static Button btn4;
    private static Button btn5;

    private static Button p1btn;
    private static Button p2btn;
    private static Button pkbtn;

    private static Button c1btn;
    private static Button ckbtn;
    private static Button pokreni;
    private static Button stopiraj;
    private static Label vrijeme;
    private static volatile boolean pauziran;
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Hello world");
        StackPane layoutVrijeme = new StackPane();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(20);

        vrijeme = new Label("Vrijeme: 0 s");
        vrijeme.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

        layoutVrijeme.getChildren().addAll(vrijeme);

        c1btn = new Button("C1");
        c1btn.setMinWidth(125);
        c1btn.setMaxWidth(125);
        c1btn.setMinHeight(50);
        c1btn.setMaxHeight(50);
        GridPane.setConstraints(c1btn, 0, 0);

        ckbtn = new Button("CK");
        ckbtn.setMinWidth(125);
        ckbtn.setMaxWidth(125);
        ckbtn.setMinHeight(50);
        ckbtn.setMaxHeight(50);
        GridPane.setConstraints(ckbtn, 2, 0);

        p1btn = new Button("P1");
        p1btn.setMinWidth(125);
        p1btn.setMaxWidth(125);
        p1btn.setMinHeight(50);
        p1btn.setMaxHeight(50);
        GridPane.setConstraints(p1btn, 0, 1);

        p2btn = new Button("P2");
        p2btn.setMinWidth(125);
        p2btn.setMaxWidth(125);
        p2btn.setMinHeight(50);
        p2btn.setMaxHeight(50);
        GridPane.setConstraints(p2btn, 1, 1);

        pkbtn = new Button("PK");
        pkbtn.setMinWidth(125);
        pkbtn.setMaxWidth(125);
        pkbtn.setMinHeight(50);
        pkbtn.setMaxHeight(50);
        GridPane.setConstraints(pkbtn, 2, 1);

        btn1 = new Button("1");
        btn1.setMinWidth(125);
        btn1.setMaxWidth(125);
        btn1.setMinHeight(50);
        btn1.setMaxHeight(50);
        GridPane.setConstraints(btn1, 1, 2);

        btn2 = new Button("2");
        btn2.setMinWidth(125);
        btn2.setMaxWidth(125);
        btn2.setMinHeight(40);
        btn2.setMaxHeight(40);
        GridPane.setConstraints(btn2, 1, 3);

        btn3 = new Button("3");
        btn3.setMinWidth(125);
        btn3.setMaxWidth(125);
        btn3.setMinHeight(40);
        btn3.setMaxHeight(40);
        GridPane.setConstraints(btn3, 1, 4);

        btn4 = new Button("4");
        btn4.setMinWidth(125);
        btn4.setMaxWidth(125);
        btn4.setMinHeight(40);
        btn4.setMaxHeight(40);
        GridPane.setConstraints(btn4, 1, 5);

        btn5 = new Button("5");
        btn5.setMinWidth(125);
        btn5.setMaxWidth(125);
        btn5.setMinHeight(40);
        btn5.setMaxHeight(40);
        GridPane.setConstraints(btn5, 1, 6);

        gridPane.getChildren().addAll(c1btn, ckbtn, p1btn, p2btn, pkbtn ,btn1, btn2, btn3, btn4, btn5);

        GridPane komande = new GridPane();
        komande.setPadding(new Insets(10, 10, 10, 10));
        komande.setVgap(10);
        komande.setHgap(200);

        pokreni = new Button("Nastavi");
        pokreni.setMinWidth(100);
        pokreni.setMaxWidth(100);
        pokreni.setMinHeight(35);
        pokreni.setMaxHeight(35);
        pokreni.setOnAction(e -> {
            if(pauziran)
            {
                Initializator.pokreniSimulaciju();
                pauziran = false;
            }
    });
        GridPane.setConstraints(pokreni, 0, 0);

        stopiraj = new Button("Pauziraj");
        stopiraj.setMinWidth(100);
        stopiraj.setMaxWidth(100);
        stopiraj.setMinHeight(35);
        stopiraj.setMaxHeight(35);
        stopiraj.setOnAction(e -> {
            if(!pauziran)
            {
                Initializator.stopirajSimulaciju();
                pauziran = true;
            }
        });
        GridPane.setConstraints(stopiraj, 1, 0);

        komande.getChildren().addAll(pokreni, stopiraj);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.getChildren().addAll(layoutVrijeme, new Separator(), gridPane, new Separator(), komande);

        primaryStage.setScene(new Scene(vBox, 450, 480));
        primaryStage.show();

        Log logGui = new Log();
        logGui.start(new Stage());
        guiInitializedLatch.countDown();
    }

    public static CountDownLatch getGuiInitializedLatch() {
        return guiInitializedLatch;
    }

    public static boolean isPauziran() {
        return pauziran;
    }

    public synchronized static void setBtn1Text(String text)
    {
        Platform.runLater(() -> {
            btn1.setText(text);
        });
    }

    public synchronized static void setBtn1Color(String text)
    {
        Platform.runLater(() -> {
            btn1.setStyle(text);
        });
    }

    public synchronized static void setBtn1OnAction(Vozilo vozilo)
    {
        btn1.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setBtn2Text(String text)
    {
        Platform.runLater(() -> {
            btn2.setText(text);
        });
    }

    public synchronized static void setBtn2Color(String text)
    {
        Platform.runLater(() -> {
            btn2.setStyle(text);
        });
    }

    public synchronized static void setBtn2OnAction(Vozilo vozilo)
    {
        btn2.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setBtn3Text(String text)
    {
        Platform.runLater(() -> {
            btn3.setText(text);
        });
    }

    public synchronized static void setBtn3Color(String text)
    {
        Platform.runLater(() -> {
            btn3.setStyle(text);
        });
    }

    public synchronized static void setBtn3OnAction(Vozilo vozilo)
    {
        btn3.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setBtn4Text(String text)
    {
        Platform.runLater(() -> {
            btn4.setText(text);
        });
    }

    public synchronized static void setBtn4Color(String text)
    {
        Platform.runLater(() -> {
            btn4.setStyle(text);
        });
    }

    public synchronized static void setBtn4OnAction(Vozilo vozilo)
    {
        btn4.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setBtn5Text(String text)
    {
        Platform.runLater(() -> {
            btn5.setText(text);
        });
    }

    public synchronized static void setBtn5Color(String text)
    {
        Platform.runLater(() -> {
            btn5.setStyle(text);
        });
    }

    public synchronized static void setBtn5OnAction(Vozilo vozilo)
    {
        btn5.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setP1btnText(String text)
    {
        Platform.runLater(() -> {
            p1btn.setText(text);
        });
    }

    public synchronized static void setP1btnColor(String text)
    {
        Platform.runLater(() -> {
            p1btn.setStyle(text);
        });
    }

    public synchronized static void setP1btnOnAction(Vozilo vozilo)
    {
        p1btn.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setP2btnText(String text)
    {
        Platform.runLater(() -> {
            p2btn.setText(text);
        });
    }

    public synchronized static void setP2btnColor(String text)
    {
        Platform.runLater(() -> {
            p2btn.setStyle(text);
        });
    }

    public synchronized static void setP2btnOnAction(Vozilo vozilo)
    {
        p2btn.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setPkbtnText(String text)
    {
        Platform.runLater(() -> {
            pkbtn.setText(text);
        });
    }

    public synchronized static void setPkbtnColor(String text)
    {
        Platform.runLater(() -> {
            pkbtn.setStyle(text);
        });
    }

    public synchronized static void setPkbtnOnAction(Vozilo vozilo)
    {
        pkbtn.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setc1btnText(String text)
    {
        Platform.runLater(() -> {
            c1btn.setText(text);
        });
    }

    public synchronized static void setc1btnColor(String text)
    {
        Platform.runLater(() -> {
            c1btn.setStyle(text);
        });
    }

    public synchronized static void setc1btnOnAction(Vozilo vozilo)
    {
        c1btn.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setCkbtnText(String text)
    {
        Platform.runLater(() -> {
            ckbtn.setText(text);
        });
    }

    public synchronized static void setCkbtnColor(String text)
    {
        Platform.runLater(() -> {
            ckbtn.setStyle(text);
        });
    }

    public synchronized static void setCkbtnOnAction(Vozilo vozilo)
    {
        ckbtn.setOnAction(e -> prikaziVozilo(vozilo));
    }

    public synchronized static void setVrijeme(String text)
    {
        Platform.runLater(() -> {
            vrijeme.setText(text);
        });
    }

    private static void prikaziVozilo(Vozilo vozilo)
    {
        if(vozilo != null)
        {
            VoziloView voziloView = new VoziloView();
            voziloView.setVozilo(vozilo);
            voziloView.start(new Stage());
        }
    }

    @Override
    public void run()
    {
        launch();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void pokreniAplikaciju(String[] args) {
        launch(args);
    }
}
