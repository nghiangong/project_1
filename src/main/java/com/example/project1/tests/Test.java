package com.example.project1.tests;

import com.example.project1.Main2;
import com.example.project1.controller.EdgeView;
import com.example.project1.controller.GraphView;
import com.example.project1.controller.MaxFlowView;
import com.example.project1.controller.VertexView;
import com.example.project1.model.Edge;
import com.example.project1.model.Vertex;

import java.util.ArrayList;

public class Test {
    static GraphView graphView = Main2.graphView;
    static MaxFlowView maxFlowView = Main2.maxFlowView;
    static public ArrayList<VertexView> vertices = new ArrayList<>();
    static public ArrayList<EdgeView> edges = new ArrayList<>();
    static public  void run() {
        graphView.addVertexView(77.60000000000002, 260.0);
        graphView.addVertexView(196.0, 108.0);
        graphView.addVertexView(211.2, 335.2);
        graphView.addVertexView(392.79999999999995, 94.4);
        graphView.addVertexView(399.20000000000005, 324.0);
        graphView.addVertexView(558.4, 204.8);
        for (Vertex vertex : graphView.vertices)
            vertices.add((VertexView) vertex);
        maxFlowView.setSourceView(vertices.get(0));
        maxFlowView.setSinkView(vertices.get(5));
        graphView.addEdgeView(vertices.get(0), vertices.get(1));
        graphView.addEdgeView(vertices.get(0), vertices.get(2));
        graphView.addEdgeView(vertices.get(2), vertices.get(1));
        graphView.addEdgeView(vertices.get(1), vertices.get(3));
        graphView.addEdgeView(vertices.get(2), vertices.get(4));
        graphView.addEdgeView(vertices.get(1), vertices.get(4));
        graphView.addEdgeView(vertices.get(4), vertices.get(3));
        graphView.addEdgeView(vertices.get(3), vertices.get(5));
        graphView.addEdgeView(vertices.get(4), vertices.get(5));
        for (Edge edge : graphView.edges)
            edges.add((EdgeView) edge);
        edges.get(0).setCapacity(7);
        edges.get(1).setCapacity(4);
        edges.get(2).setCapacity(3);
        edges.get(3).setCapacity(5);
        edges.get(4).setCapacity(2);
        edges.get(5).setCapacity(3);
        edges.get(6).setCapacity(3);
        edges.get(7).setCapacity(8);
        edges.get(8).setCapacity(5);
    }
}
