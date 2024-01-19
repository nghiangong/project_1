package com.example.project1.controller;

import com.example.project1.Main2;
import com.example.project1.State;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Buttons {
    private GraphView graphView = Main2.graphView;
    private HBox view = new HBox();
    private Text text = new Text();

    Button selectedButton = null;

    private Button createButton(String label, State state) {
        Button button = new Button(label);
        button.setCursor(Cursor.HAND);
        button.setOnMouseClicked(event -> {
            text.setText(getInstructionText(label));
            if (state != State.MAXFLOW) {
                Main2.showInfor(text);
                Main2.graphView.hideMaxFlow();
            }
            else {
                Main2.maxFlowView.showInfo();
            }
            graphView.setState(state);
            if (selectedButton != null) selectedButton.setStyle("");
            button.setStyle("-fx-background-color: lightblue;");
            selectedButton = button;
        });
        return button;
    }

    private String getInstructionText(String label) {
        switch (label) {
            case "Mouse":
                return "Click a Node or Edge to view its info.";
            case "Add Node":
                return "Click anywhere to add a Node.";
            case "Remove Node":
                return "Click a Node to remove it.";
            case "Add Edge":
                return "Click two Nodes to add an Edge.";
            case "Remove Edge":
                return "Click an Edge to remove it or click two Nodes.";
            case "Max Flow":
                return ""; // Add Max Flow instruction if needed
            default:
                return "";
        }
    }

    public Buttons() {
        Button mouseButton = createButton("Mouse", State.MOUSE);
        Button addNodeButton = createButton("Add Node", State.ADDNODE);
        Button removeNodeButton = createButton("Remove Node", State.REMOVENODE);
        Button addEdgeButton = createButton("Add Edge", State.ADDEGDE);
        Button removeEdgeButton = createButton("Remove Edge", State.REMOVEEGDE);
        Button maxFlowButton = createButton("Max Flow", State.MAXFLOW);

        view.getChildren().addAll(mouseButton, addNodeButton, removeNodeButton,
                addEdgeButton, removeEdgeButton, maxFlowButton);
        view.setSpacing(10);
        view.setPadding(new Insets(0, 0, 10, 10));

        text.setFont(new Font(24));
        text.setWrappingWidth(300);
    }
    public HBox getView() {
        return view;
    }
}
