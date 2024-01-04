package org.etfbl.pj2.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application implements Runnable
{
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
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Hello world");
        StackPane layout = new StackPane();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(20);

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


        primaryStage.setScene(new Scene(gridPane, 450, 400));
        primaryStage.show();
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
