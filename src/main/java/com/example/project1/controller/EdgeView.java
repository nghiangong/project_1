package com.example.project1.controller;

import com.example.project1.Main2;
import com.example.project1.model.Edge;
import com.example.project1.model.MaxFlow;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class EdgeView extends Edge{
    public GraphView graphView = (GraphView) graph;
    private Group group = new Group();
    private Line line = new Line();
    private Text text = new Text();
    private Polygon arrow = new Polygon();
    private FlowPane info = new FlowPane();
    Text path = new Text();
    Label capacityLabel = new Label("Capacity: ");
    TextField capacityTextField = new TextField(capacity.toString());
    private Paint defaultColor = Color.BLACK;

    private double X1;
    private double Y1;
    private double X2;
    private double Y2;
    public EdgeView(VertexView vertexView1, VertexView vertexView2) {
        super(vertexView1, vertexView2);
        this.X1 = vertexView1.getX();
        this.Y1 = vertexView1.getY();
        this.X2 = vertexView2.getX();
        this.Y2 = vertexView2.getY();

        line.setStrokeWidth(2);

        text.setText("");
        text.setFont(new Font(20));

        path.setFont(new Font(24));
        capacityLabel.setFont(new Font(24));
        capacityTextField.setFont(new Font(24));

        info.setMaxWidth(300);
        info.getChildren().addAll(capacityLabel, capacityTextField, path);

        setLocation();
        setOnMouse();

        group.getChildren().addAll(line, arrow, text);
    }

    public void setColor(Paint color) {
        line.setStroke(color);
        text.setFill(color);
        arrow.setFill(color);
    }
    public void setOnMouse() {
        group.setOnMouseClicked(event -> {
            switch (GraphView.state) {
                case MOUSE -> {
                    graphView.setSelectedItem(group);
                    showInfo();
                }
                case ADDNODE -> {
                }
                case REMOVENODE -> {
                }
                case ADDEGDE -> {
                }
                case REMOVEEGDE -> {
                    graphView.removeEdgeView(this);
                }
            }
        });
        group.setOnMouseEntered(event -> {
            switch (GraphView.state) {
                case MOUSE -> {
                    group.setCursor(Cursor.HAND);
                    setColor(Color.YELLOWGREEN);
                }
                case ADDNODE -> {
                }
                case REMOVENODE -> {
                }
                case ADDEGDE -> {
                }
                case REMOVEEGDE -> {
                    group.setCursor(Cursor.HAND);
                    setColor(Color.YELLOWGREEN);
                }
            }
        });
        group.setOnMouseExited(event -> {
            switch(GraphView.state) {
                case MOUSE -> {
                    group.setCursor(Cursor.DEFAULT);
                    setColor(defaultColor);
                }
                case ADDNODE -> {
                }
                case REMOVENODE -> {
                }
                case ADDEGDE -> {
                }
                case REMOVEEGDE -> {
                    group.setCursor(Cursor.DEFAULT);
                    setColor(defaultColor);
                }
            }
        });

        capacityTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume(); // Hủy bỏ sự kiện nếu không phải số hoặc vượt quá giới hạn
            }
        });
        capacityTextField.setOnAction(event -> {
            String input = capacityTextField.getText();
            if (input != null && input != "") setCapacity(Integer.parseInt(input));
        });
    }
    public void addFlow(int c) {
        flow += c;
        text.setText(flow + "/" + capacity);
        setTextLocation();
    }
    public void setFlow(int c) {
        flow = c;
        text.setText(flow + "/" + capacity);
        setTextLocation();
    }
    public void setCapacity(int c) {
        if (c != 0){
            capacity = c;
            text.setText(flow + "/" + capacity);
            setTextLocation();
        }
    }
    public void showInfo() {
        capacityTextField.setText(capacity.toString());
        path.setText("Path: " + startVertex.name + " --> " + endVertex.name);
        Main2.showInfor(info);
    }
    public Node getView() {return group;}
    public void setLocation() {
        line.setStartX(X1);
        line.setStartY(Y1);
        line.setEndX(X2);
        line.setEndY(Y2);

        setTextLocation();

        setArrowLocation();
    }
    public void setTextLocation() {
        double angle = Math.atan2(Y2-Y1, X2-X1);
        double midX = (X1 + X2) / 2;
        double midY = (Y1 + Y2) / 2;
        double height = text.getFont().getSize();
        double width = text.getLayoutBounds().getWidth();
        double distance = Math.sqrt(height*height + width*width)/2;
        double newX = midX + distance * Math.sin(angle);
        double newY = midY - distance * Math.cos(angle);

        text.setX(newX - width/2);
        text.setY(newY + height / 4);
    }

    public void setArrowLocation() {
        arrow.setFill(Color.BLACK);
        double arrowLength = 10;
        double arrowWidth =  5;
        double R = VertexView.radius;

        double angle = Math.atan2(Y2 - Y1, X2 - X1);
        double newX = X2 - (R+arrowLength)*Math.cos(angle);
        double newY = Y2 - (R+arrowLength)*Math.sin(angle);

        double arrowTipX = newX + arrowLength * Math.cos(angle);
        double arrowTipY = newY + arrowLength * Math.sin(angle);

        double arrowLeftX = newX + arrowWidth * Math.cos(angle + Math.PI / 2);
        double arrowLeftY = newY + arrowWidth * Math.sin(angle + Math.PI / 2);

        double arrowRightX = newX + arrowWidth * Math.cos(angle - Math.PI / 2);
        double arrowRightY = newY + arrowWidth * Math.sin(angle - Math.PI / 2);

        arrow.getPoints().addAll(arrowTipX, arrowTipY, arrowLeftX, arrowLeftY, arrowRightX, arrowRightY);
    }
}
