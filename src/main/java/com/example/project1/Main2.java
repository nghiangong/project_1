package com.example.project1;

import com.example.project1.controller.Buttons;
import com.example.project1.controller.GraphView;
import com.example.project1.controller.MaxFlowView;
import com.example.project1.tests.Test;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main2 extends Application {
    public static GraphView graphView = new GraphView();
    public static MaxFlowView maxFlowView = new MaxFlowView();
    Buttons buttons = new Buttons();

    HBox hBox = new HBox();
    VBox vBox = new VBox();
    static ScrollPane scrollPane = new ScrollPane();

    @Override
    public void start(Stage stage) throws Exception {
        Text text = new Text("Click a button to get started.");
        text.setFont(new Font(24));
        text.setWrappingWidth(300);
        scrollPane.setMinWidth(300);
        scrollPane.setMaxWidth(300);
        scrollPane.setFitToWidth(false);
        scrollPane.setContent(text);

        hBox.getChildren().add(scrollPane);
        hBox.getChildren().add(vBox);
        HBox.setHgrow(vBox, javafx.scene.layout.Priority.ALWAYS);
        vBox.getChildren().add(buttons.getView());
        vBox.getChildren().add(graphView.getView());
        VBox.setVgrow(graphView.getView(), javafx.scene.layout.Priority.ALWAYS);

        Scene scene = new Scene(hBox, 1000, 800);
        stage.setScene(scene);
        stage.show();
//        Test.run();
    }
    public static void showInfor(Node children) {
        scrollPane.setContent(children);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
