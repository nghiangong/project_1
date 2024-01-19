package com.example.project1.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class Graph {
	public ArrayList<Vertex> vertices = new ArrayList<>();
	public ArrayList<Edge> edges = new ArrayList<>();

	public boolean addVertex(Vertex vertex) {
		return vertices.add(vertex);
	}
	public boolean removeVertex(Vertex vertex){
		return vertices.remove(vertex);
	}
	public boolean addEdge(Edge edge){
		edge.startVertex.addEdge(edge);
		edge.endVertex.addEdge(edge);
		return edges.add(edge);
	}
	public boolean removeEdge(Edge edge){
		edge.startVertex.removeEdge(edge);
		edge.endVertex.removeEdge(edge);
		return edges.remove(edge);
	}

}
