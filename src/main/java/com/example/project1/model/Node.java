package com.example.project1.model;

import java.util.Hashtable;

public class Node {
	public int num;
	public Hashtable<Node, Edge> edges;

	public Node(int num) {
		this.num = num;
		edges = new Hashtable<Node, Edge>();
	}
}
