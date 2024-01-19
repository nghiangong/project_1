package com.example.project1.model;

import com.example.project1.Main2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Vertex {
    public Graph graph = Main2.graphView;
    public String name;

    public HashMap<Vertex, Edge> outgoingEdges = new HashMap<>();
    public HashMap<Vertex, Edge> incomingEdges = new HashMap<>();

    public Vertex(String name) {
        this.name = name;
    }

    public boolean addEdge(Edge edge) {
        if (edge.startVertex == this) outgoingEdges.put(edge.endVertex, edge);
        else incomingEdges.put(edge.startVertex, edge);
        return true;
    }
    public boolean removeEdge(Edge edge) {
        if (edge.startVertex == this)
            return outgoingEdges.remove(edge.endVertex, edge);
        else return incomingEdges.remove(edge.startVertex,edge);
    }
    public Collection<Edge> getAllEdges() {
        Collection<Edge> edges = new ArrayList<>();
        edges.addAll(outgoingEdges.values());
        edges.addAll(incomingEdges.values());
        return edges;
    }

}
