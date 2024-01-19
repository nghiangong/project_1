package com.example.project1.model;

import com.example.project1.Main2;
import com.example.project1.controller.VertexView;
import javafx.util.Pair;

import java.util.*;

public class MaxFlow {
    public Graph graph = Main2.graphView;
    public Stack<Pair<Integer, ArrayList<Vertex>>> paths = new Stack<>();
    public ArrayList<Vertex> vertices= graph.vertices;
    public ArrayList<Edge> edges = graph.edges;
    public Vertex source;
    public Vertex sink;
    public int[][] adj ;
    public int[] parent ;
    public int maxFlow = 0;
    int s;
    int t;
    int n ;
    int bfs() {
        parent = new int[n];
        for (int i = 0; i < vertices.size() ; i++)
            parent[i] = -1;
        parent[s]= -2;
        Queue<Pair<Integer, Integer>> q = new LinkedList<>();
        q.add(new Pair<>(s, Integer.MAX_VALUE));

        while (!q.isEmpty()) {
            Pair<Integer, Integer> top = q.poll();
            int cur = top.getKey();
            int flow = top.getValue();
            for (int i =0; i < n; i++) {
                int next = i;
                if (parent[next] == -1 && adj[cur][next] > 0) {
                    parent[next] = cur;
                    int new_flow = Math.min(flow, adj[cur][next]);
                    if (next == t) return new_flow;
                    q.add(new Pair<>(next, new_flow));
                }
            }
        }
        return 0;
    }
    int maxFlow() {
        init();
        int flow = 0;
        int new_flow;
        while ((new_flow = bfs()) > 0) {
            flow += new_flow;
            int cur = t;
            ArrayList<Vertex> path = new ArrayList<>();
            path.add(0, vertices.get(cur));
            while (cur != s) {
                System.out.print(cur + " <- ");
                int prev = parent[cur];
                adj[prev][cur] -= new_flow;
                adj[cur][prev] += new_flow;
                cur = prev;
                path.add(0, vertices.get(cur));
            }
            System.out.print(cur + " : " + new_flow);
            System.out.println();
            paths.add(0, new Pair<>(new_flow, path));
        }
        maxFlow = flow;
        return flow;
    }
    void init() {
        n = vertices.size();
        adj = new int[n][n];
        s = vertices.indexOf(source);
        t = vertices.indexOf(sink);
        paths.clear();
        for (Edge edge : edges) {
            int u = vertices.indexOf(edge.startVertex);
            int v = vertices.indexOf(edge.endVertex);
            int c = edge.capacity;
            adj[u][v] = c;
            adj[v][u] = 0;
        }
    }
    public void run() {
        init();
        System.out.println(maxFlow());
    }
}
