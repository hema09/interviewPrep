package javasrc.problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Hema on 3/7/2016.
 */

public class Graph {
     public enum State {
        Unvisited, Visited, Visiting;
    }
    ArrayList<Node> nodes;
    ArrayList<Node> getNodes() {return nodes;}
    public void addNode(Node first, Node... n) {
        if(nodes == null)
            nodes = new ArrayList<Node>();
        nodes.add(first);
        if(n != null)
            for(Node n1 : n)
                nodes.add(n1);
    }
    class Node<T> {
        private T value;
        State state;
        public Node(T val) {
            value = val;
            state = State.Unvisited;
        }
        private ArrayList<Node> neighbors;
        public ArrayList<Node> getNeighbors() {
            return neighbors;
        }
        public T getValue() {return value;}
        public void addNeighbor(Node n) {
            neighbors.add(n);
        }
        public void addNeighbors(Node n, Node... nodes) {
            if(neighbors == null)
                neighbors = new ArrayList<Node>();
            neighbors.add(n);
            if(nodes != null) {
                for(Node nd : nodes)
                    neighbors.add(nd);
            }
        }
    }

    // bfs 1 - iterative - use queue
    public boolean pathExists(Graph graph, Node start, Node end) {
        for(Node n : graph.nodes) {
            n.state = State.Unvisited;
        }
        Queue<Node> que = new LinkedList<Node>();
        que.add(start);
        start.state = State.Visiting;
        while(!que.isEmpty()) {
            Node n = que.remove();
            if(n == end)
                return true;
            ArrayList<Node> neighbors = n.getNeighbors();
            if(neighbors != null) {
            for(Graph.Node neighbor : neighbors) {
                if(neighbor.state == State.Unvisited) {
                    neighbor.state = State.Visiting;
                    que.add(neighbor);
                }
            }}
            n.state = State.Visited;
        }
        return false;
    }

    // dfs 2 - recursive stack
    public boolean pathExists2(Graph g, Node start, Node end) {
        if(start == end) return true;
        start.state = State.Visited;
        ArrayList<Node> neighbors = start.getNeighbors();
        if(neighbors != null) {
            for(Node n : neighbors) {
                if(n.state == State.Unvisited) {
                    if(pathExists2(g, n, end))
                        return true;
                }
            }
        }
        return false;
    }

    public Graph sampleGraph() {
        Graph g = new Graph();
        Node a = new Node('a');
        Node b = new Node('b');
        Node c = new Node('c');
        Node d = new Node('d');
        Node e = new Node('e');
        a.addNeighbors(b, c, d);
        b.addNeighbors(c, d);
        c.addNeighbors(d);
        g.addNode(a, b, c, d, e);
        return g;
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        g = g.sampleGraph();
        System.out.println(g.pathExists2(g, g.getNodes().get(0), g.getNodes().get(3)));
    }
}


