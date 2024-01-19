package com.example.project1.model;

import com.example.project1.Main2;

public class Edge {
	public Graph graph = Main2.graphView;
	public Vertex startVertex;
	public Vertex endVertex;
	public Integer capacity = 0;
	public Integer flow = 0;

	public Integer residual = 0;

	public Edge(Vertex startVertex, Vertex endVertex, Integer capacity) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
		this.capacity = capacity;
	}
	public Edge(Vertex startVertex, Vertex endVertex) {
		this.startVertex = startVertex;
		this.endVertex = endVertex;
	}


}
