package com.example.project1.controller;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VirtualEdge {
    private int flow;
    private double X1;
    private double Y1;
    private double X2;
    private double Y2;
    private Group group = new Group();
    private Line line = new Line();
    private Text text = new Text();
    private Polygon arrow = new Polygon();
    public VirtualEdge(VertexView vertexView1, VertexView vertexView2, int flow) {
        this.X1 = vertexView1.getX();
        this.Y1 = vertexView1.getY();
        this.X2 = vertexView2.getX();
        this.Y2 = vertexView2.getY();
        this.flow = flow;
        initializeUI();
    }
    public VirtualEdge(VertexView vertexView1, VertexView vertexView2) {
        this.X1 = vertexView1.getX();
        this.Y1 = vertexView1.getY();
        this.X2 = vertexView2.getX();
        this.Y2 = vertexView2.getY();
        initializeUI();
    }
    public void initializeUI() {
        line.setStrokeWidth(7);
        line.setStartX(X1);
        line.setStartY(Y1);
        line.setEndX(X2);
        line.setEndY(Y2);


        if (flow > 0) text.setText(flow+"");
        text.setFont(new Font(20));
        setTextLocation();

        arrow.setFill(Color.BLACK);
        setArrowLocation();

        setColor(Color.web("#8181ff"));

        group.getChildren().addAll(line, text, arrow);
    }
    public void setColor(Paint color) {
        line.setStroke(color);
        text.setFill(color);
        arrow.setFill(color);
    }
    public void setTextLocation() {
        double angle = Math.atan2(Y2-Y1, X2-X1) + Math.PI;
        double midX = (X1 + X2) / 2;
        double midY = (Y1 + Y2) / 2;
        double height = text.getFont().getSize();
        double width = text.getLayoutBounds().getWidth();
        double distance = Math.sqrt(height*height + width*width)/2 + 2;
        double newX = midX + distance * Math.sin(angle);
        double newY = midY - distance * Math.cos(angle);

        text.setX(newX - width/2);
        text.setY(newY + height / 4);
    }
    public void setArrowLocation() {
        double arrowLength = 15;
        double arrowWidth =  10;
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

    public Node getView() {
        return group;
    }
}
