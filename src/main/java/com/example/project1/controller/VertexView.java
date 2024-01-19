package com.example.project1.controller;

import com.example.project1.Main2;
import com.example.project1.Name;
import com.example.project1.model.Edge;
import com.example.project1.model.Vertex;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VertexView extends Vertex {
    private Group group = new Group();
    private GraphView graphView = (GraphView) graph;
    private Circle circle = new Circle(radius);
    public Text text = new Text();
    private FlowPane info =  new FlowPane();
    private Label nameLabel = new Label("Name: ");
    private TextField nameTextField = new TextField(name);
    private Label edgesLabel = new Label("Edges: ");
    private Text edgesText = new Text();

    public static double radius = 30;
    private Double X;
    private Double Y;
    Paint defaultColor = Color.web("#dedede");

    public VertexView(Double X, Double Y) {
        super(Name.getRandomName());
        this.X = X;
        this.Y = Y;

        circle.setFill(defaultColor);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);

        text.setText(name);
        text.setFont(new Font(24));

        nameLabel.setFont(new Font(24));
        nameTextField.setFont(new Font(24));
        edgesLabel.setFont(new Font(24));
        edgesText.setFont(new Font(24));
        edgesText.setWrappingWidth(300);
        info.setMaxWidth(300);
        info.getChildren().addAll(nameLabel, nameTextField,edgesLabel, edgesText);

        setLocation();
        group.getChildren().addAll(circle, text);

        setOnMouse();
    }

    public void setOnMouse(){
        group.setOnMouseClicked(event -> {
            switch (GraphView.state) {
                case MOUSE -> {
                    graphView.setSelectedItem(group);
                    showInfo();
                }
                case ADDNODE -> {
                    event.consume();
                }
                case REMOVENODE -> {
                    graphView.removeVertexView(this);
                }
                case ADDEGDE -> {
                    graphView.setSelectedVertexView(this);
                }
                case REMOVEEGDE -> {
                }
                case MAXFLOW -> {
                    Main2.maxFlowView.setVertex(this);
                }
            }
        });
        group.setOnMouseEntered(event -> {
            switch (GraphView.state) {
                case MOUSE -> {
                    circle.setFill(Color.YELLOWGREEN);
                    group.setCursor(Cursor.HAND);
                }
                case ADDNODE -> {
                }
                case REMOVENODE -> {
                    circle.setFill(Color.YELLOWGREEN);
                    group.setCursor(Cursor.HAND);
                }
                case ADDEGDE -> {
                    group.setCursor(Cursor.HAND);
                    circle.setFill(Color.YELLOWGREEN);
                }
                case REMOVEEGDE -> {
                }
                case MAXFLOW -> {
                    group.setCursor(Cursor.HAND);
                    circle.setFill(Color.YELLOWGREEN);
                }
            }
        });
        group.setOnMouseExited(event -> {
            switch (GraphView.state) {
                case MOUSE -> {
                    circle.setFill(defaultColor);
                    group.setCursor(Cursor.DEFAULT);
                }
                case ADDNODE -> {
                }
                case REMOVENODE -> {
                    circle.setFill(defaultColor);
                    group.setCursor(Cursor.DEFAULT);
                }
                case ADDEGDE -> {
                    circle.setFill(defaultColor);
                    group.setCursor(Cursor.DEFAULT);
                }
                case REMOVEEGDE -> {
                }
                case MAXFLOW -> {
                    circle.setFill(defaultColor);
                    group.setCursor(Cursor.DEFAULT);
                }
            }

        });
        nameTextField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String character = event.getCharacter();
            if (!character.matches("[a-zA-Z0-9]")
                || nameTextField.getText().length() >= 4) {
                event.consume(); // Hủy sự kiện nếu ký tự không phải là chữ hoặc số
            }
        });
        nameTextField.setOnAction(event -> {
            String input = nameTextField.getText().replaceAll("\\s", "");
            if (input != "") setName(input);
        });
    }
    public void setName(String string) {
        name = string;
        text.setText(string);
        setTextLocation();
    }

    public Node getView() {
        return group;
    }
    public void setSelected(boolean b) {
        if (b) group.setEffect(new DropShadow());
        else group.setEffect(null);
    }
    public void setSourceOrSink(boolean b){
        if (b) {
            defaultColor = Color.web("f4a957");
            circle.setFill(defaultColor);
        }
        else {
            defaultColor = Color.web("#dedede");
            circle.setFill(defaultColor);
        }
    }
    public void showInfo() {
        nameTextField.setText(name);

        String text = new String();
        for (Edge edge : outgoingEdges.values())
            text = text + " --> " +edge.endVertex.name + " : " + edge.capacity + "\n";
        for (Edge edge : incomingEdges.values())
            text = text + " <-- " + edge.startVertex.name + " : " + edge.capacity + "\n";


        edgesText.setText(text);
        Main2.showInfor(info);

    }
    public void setTextLocation() {
        text.setX(X - text.getBoundsInLocal().getWidth() / 2);
        text.setY(Y + text.getBoundsInLocal().getHeight() / 4);
    }
    public void setLocation() {
        circle.setCenterX(X);
        circle.setCenterY(Y);

        setTextLocation();
    }
    public void setMaxFlow(boolean b) {
        if (b) circle.setStroke(Color.web("#8181ff"));
        else circle.setStroke(Color.BLACK);
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }


}
