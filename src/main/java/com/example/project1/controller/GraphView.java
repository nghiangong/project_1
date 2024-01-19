package com.example.project1.controller;

import com.example.project1.State;
import com.example.project1.model.Edge;
import com.example.project1.model.Graph;
import com.example.project1.model.Vertex;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GraphView extends Graph {
    public static State state = State.MOUSE;
    private VertexView selectedVertexView;
    private Node selectedItem;
    Pane pane = new Pane();
    public GraphView() {
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
        pane.setBorder(new Border(borderStroke));

        setOnMouse();
    }
    public void setOnMouse() {
        pane.setOnMouseClicked(event -> {
            if (state == State.ADDNODE) {
                addVertexView(event.getX(), event.getY());
            }
        });
    }

    public void setSelectedItem(Node item) {
        if (item != null) {
            if (selectedItem != null) selectedItem.setEffect(null);
            selectedItem = item;
            item.setEffect(new DropShadow());
        } else {
            if (selectedItem != null) selectedItem.setEffect(null);
        }
    }
    public void setSelectedVertexView(VertexView vertexView) {
        if (vertexView == null) {
            if (selectedVertexView != null) {
                selectedVertexView.setSelected(false);
            }
        } else
            if (selectedVertexView == vertexView) {
                vertexView.setSelected(false);
                selectedVertexView = null;
            } else
                if (selectedVertexView == null){
                    selectedVertexView = vertexView;
                    vertexView.setSelected(true);
                } else {
                    addEdgeView(selectedVertexView, vertexView);
                    selectedVertexView.setSelected(false);
                    selectedVertexView = null;
                }
    }
    public void setState(State s) {
        state = s;
        setSelectedVertexView(null);
        setSelectedItem(null);
    }

    public boolean addVertexView(Double X, Double Y) {
        Double radius = VertexView.radius;
        if (X >= radius && Y>= radius) {
            VertexView vertexView = new VertexView(X, Y);
            super.addVertex(vertexView);
            pane.getChildren().add(vertexView.getView());
//        System.out.println("graphView.addVertexView("+X+", "+Y+");");
//        vertices.add(vertexView);
            return true;
        }
        return false;
    }
    public boolean removeVertexView(VertexView vertexView) {

        for (Edge edge : vertexView.getAllEdges()) {
            EdgeView edgeView = (EdgeView) edge;
            super.removeEdge(edgeView);
            pane.getChildren().remove(edgeView.getView());
        }
        super.removeVertex(vertexView);
        return pane.getChildren().remove(vertexView.getView());
    }
    public boolean addEdgeView(VertexView vertexView1, VertexView vertexView2) {
//        System.out.println("graphView.addEdgeView(vertices.get("+vertices.indexOf(vertexView1)+
//                "), vertices.get("+vertices.indexOf(vertexView2)+"));");
        EdgeView edgeView = new EdgeView(vertexView1, vertexView2);
        super.addEdge(edgeView);
        pane.getChildren().add(edgeView.getView());
        edgeView.getView().toBack();
        return true;
    }
    public boolean removeEdgeView(EdgeView edgeView){
        super.removeEdge(edgeView);
        return pane.getChildren().remove(edgeView.getView());
    }

    public Group virtualEdgesGroup = new Group();
    public boolean showMaxFlow() {
        virtualEdgesGroup.getChildren().clear();
        pane.getChildren().remove(virtualEdgesGroup);
        for (Edge edge : edges) {
            EdgeView edgeView = (EdgeView) edge;
            if (edgeView.flow > 0) {
                VertexView start = (VertexView) edgeView.startVertex;
                VertexView end = (VertexView) edgeView.endVertex;
                start.setMaxFlow(true);
                end.setMaxFlow(true);
                VirtualEdge virtualEdge = new VirtualEdge(start, end);
                virtualEdgesGroup.getChildren().add(virtualEdge.getView());
            }
        }
        pane.getChildren().add(virtualEdgesGroup);
        virtualEdgesGroup.toBack();
        return true;
    }
    public boolean hideMaxFlow() {
        virtualEdgesGroup.getChildren().clear();
        for (Vertex vertex : vertices)
            ((VertexView) vertex).setMaxFlow(false);
        clearFlow();
        pane.getChildren().remove(virtualEdgesGroup);
        return true;
    }
    ArrayList<Vertex> path;
    int cur_flow;
    public boolean addFlow(VertexView vertexView1, VertexView vertexView2, int flow) {
        EdgeView edgeView ;
        edgeView= (EdgeView) vertexView1.outgoingEdges.get(vertexView2);
        if (edgeView != null) edgeView.addFlow(flow);
        else {
            edgeView = (EdgeView) vertexView1.incomingEdges.get(vertexView2);
            edgeView.addFlow(-flow);
        }
        return true;
    }
    public boolean updateFlow() {
        if (this.path == null) return false;
        VertexView vertexView1 = (VertexView) path.get(0);
        for (int i = 1; i < path.size() ; i++) {
            VertexView vertexView2 = (VertexView) path.get(i);
            addFlow(vertexView1, vertexView2, cur_flow);
            vertexView1 = vertexView2;
        }
        clearPathView();
        return true;
    }
    public boolean clearFlow() {
        for (Edge edge : edges) {
            if (edge.flow > 0) {
                EdgeView edgeView = (EdgeView) edge;
                edgeView.setFlow(0);
            }
        }
        return true;
    }
    public boolean showPathView(Integer flow, ArrayList<Vertex> path) {
        this.path = path;
        this.cur_flow = flow;
        VertexView vertexView1 = (VertexView) path.get(0);
        vertexView1.setMaxFlow(true);
        for (int i = 1; i < path.size() ; i++) {
            VertexView vertexView2 = (VertexView) path.get(i);
            VirtualEdge virtualEdge = new VirtualEdge(vertexView1, vertexView2, flow);
            virtualEdgesGroup.getChildren().add(virtualEdge.getView());
            vertexView1 = vertexView2;
            vertexView1.setMaxFlow(true);
        }
        pane.getChildren().add(virtualEdgesGroup);
        virtualEdgesGroup.toBack();
        return true;
    }
    public boolean clearPathView() {
        for (int i = 0; i < path.size(); i++)
            ((VertexView) path.get(i)).setMaxFlow(false);
        pane.getChildren().remove(virtualEdgesGroup);
        virtualEdgesGroup.getChildren().clear();
        path = null;
        return true;
    }
    public Pane getView() {return pane;}
}
