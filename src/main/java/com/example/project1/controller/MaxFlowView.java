package com.example.project1.controller;

import com.example.project1.Main2;
import com.example.project1.model.MaxFlow;
import com.example.project1.model.Vertex;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;

public class MaxFlowView extends MaxFlow {
    GraphView graphView = Main2.graphView;
    private GridPane gridPane = new GridPane();
    private VertexView sourceView = (VertexView) source;
    private VertexView sinkView = (VertexView) sink;
    private Button selectedButton;
    private Text introText = new Text("Click button to choose");
    private Button sourceButton = new Button("Choose source node");
    private Label sourceLabel = new Label("Source node: ");
    private Text sourceText = new Text("");
    private Button sinkButton = new Button("Choose sink node");
    private Label sinkLabel = new Label("Sink node: ");
    private Text sinkText = new Text("");
    private boolean over = false;
    private Button runButton = new Button("Run");
    private Button nextButton = new Button("Next");
    private Text text = new Text();

    public MaxFlowView() {
        initializeUI();
    }

    private void initializeUI() {
        Font font = new Font(24);
        introText.setFont(font);
        sourceLabel.setFont(font);
        sourceText.setFont(font);
        sourceText.setFill(Color.web("f4a957"));
        sinkLabel.setFont(font);
        sinkText.setFont(font);
        sinkText.setFill(Color.web("f4a957"));
        text.setFont(font);
        text.setWrappingWidth(300);

        // Thêm class CSS cho các phần tử để tùy chỉnh giao diện
        sourceButton.getStyleClass().add("choose-button");
        sinkButton.getStyleClass().add("choose-button");

        // Thiết lập GridPane để đặt các phần tử trên các hàng và cột
        ColumnConstraints columnConstraints = new ColumnConstraints();
        gridPane.getColumnConstraints().add(columnConstraints);

        // Thêm các phần tử vào GridPane với setRowIndex
        gridPane.add(introText, 0, 0, 2, 1); // Span 2 cột
        gridPane.add(sourceButton, 0, 1);
        gridPane.add(sourceLabel, 0, 2);
        gridPane.add(sourceText, 1, 2);
        gridPane.add(sinkButton, 0, 3);
        gridPane.add(sinkLabel, 0, 4);
        gridPane.add(sinkText, 1, 4);
        gridPane.add(runButton, 0, 5);
        gridPane.add(nextButton, 1, 5);
        gridPane.add(text, 0, 6, 2, 1);

        sourceButton.setOnAction(event -> {setSelectedButton(sourceButton);});
        sinkButton.setOnAction(event -> {setSelectedButton(sinkButton);});
        runButton.setOnAction(event -> {
            graphView.hideMaxFlow();
            if (over == false) {
                if (vertices.contains(source) && vertices.contains(sink)) {
                    over = true;
                    runButton.setText("Over");
                    text.setText("Paths : \n");
                    setSelectedButton(runButton);
                    run();
                    nextButton.setDisable(false);
                    sinkButton.setDisable(true);
                    sourceButton.setDisable(true);
                }
            } else {
                over = false;
                runButton.setText("Run");
                nextButton.setDisable(true);
                sinkButton.setDisable(false);
                sourceButton.setDisable(false);
            }
        });
        nextButton.setOnAction(event -> {
            graphView.updateFlow();
            if (!paths.empty()) {
                Pair<Integer, ArrayList<Vertex>> top = paths.pop();
                int flow = top.getKey();
                ArrayList<Vertex> path = top.getValue();
                addPath(flow, path);
                graphView.showPathView(flow, path);

            } else {
                text.setText(text.getText() + "Max Flow: " + maxFlow);
                graphView.showMaxFlow();
                nextButton.setDisable(true);
                sinkButton.setDisable(false);
                sourceButton.setDisable(false);
                runButton.setText("Run");
                over = false;
            }
        });
    }
    public void addPath(int flow, ArrayList<Vertex> path) {
        String s = text.getText();
        s = s + path.get(0).name;

        for (int i = 1; i < path.size(); i++)
            s = s + "->" + path.get(i).name;
        s = s+ " | flow: " + flow + "\n";
        text.setText(s);
    }
    public void setSelectedButton(Button button) {
        if (selectedButton != null) selectedButton.setStyle("");
        selectedButton = button;
        button.setStyle("-fx-background-color: #ffe6cc;");
    }
    public void setVertex(VertexView vertexView) {
        if (selectedButton != null){
            if (selectedButton == sourceButton) setSourceView(vertexView);
            else if (selectedButton == sinkButton) setSinkView(vertexView);
        }
    }

    public void setSourceView(VertexView vertexView) {
        if (sourceView != null) sourceView.setSourceOrSink(false);
        source = vertexView;
        sourceView = vertexView;
        sourceView.setSourceOrSink(true);
        sourceText.setText(sourceView.name);
        if (sink != null && source != null
                && vertices.contains(sink) && vertices.contains(source))
            runButton.setDisable(false);
        else runButton.setDisable(true);
    }
    public void setSinkView(VertexView vertexView) {
        if (sinkView != null) sinkView.setSourceOrSink(false);
        sink = vertexView;
        sinkView = vertexView;
        sinkView.setSourceOrSink(true);
        sinkText.setText(sinkView.name);
        if (sink != null && source != null
                && vertices.contains(sink) && vertices.contains(source))
            runButton.setDisable(false);
        else runButton.setDisable(true);
    }
    public void run() {
        super.run();
    }
    public void checkSourceAndSink() {
        if (sink != null && source != null
                && vertices.contains(sink) && vertices.contains(source))
            runButton.setDisable(false);
        else runButton.setDisable(true);
    }

    public void showInfo() {
        Main2.showInfor(gridPane);
        checkSourceAndSink();

        sinkButton.setDisable(false);
        sourceButton.setDisable(false);
        nextButton.setDisable(true);
        runButton.setText("Run");
        text.setText("");
        over = false;
    }
}
