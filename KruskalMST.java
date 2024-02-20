//q no 3b 

import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    // Comparator function used for sorting edges
    public int compareTo(Edge compareEdge) {
        return this.weight - compareEdge.weight;
    }
}

class DisjointSet {
    int[] parent, rank;
    int n;

    // Constructor
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        this.n = n;
        makeSet();
    }

    // Creates n sets with single item in each
    void makeSet() {
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    // Returns representative of x's set
    int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Unites the set that includes x and the set that includes x
    void union(int x, int y) {
        int xRoot = find(x), yRoot = find(y);

        if (xRoot == yRoot)
            return;

        if (rank[xRoot] < rank[yRoot])
            parent[xRoot] = yRoot;
        else if (rank[yRoot] < rank[xRoot])
            parent[yRoot] = xRoot;
        else {
            parent[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }
}

public class KruskalMST {
    int V, E;    // Number of vertices and edges
    Edge[] edge; // Collection of all edges

    // Creates a graph with V vertices and E edges
    public KruskalMST(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i) {
            edge[i] = new Edge();
        }
    }

    // The main function to construct MST using Kruskal's algorithm
    void kruskalMST() {
        Edge[] result = new Edge[V];  // This will store the resultant MST
        int e = 0;  // An index variable, used for result[]
        int i = 0;  // An index variable, used for sorted edges

        // Step 1: Sort all the edges in non-decreasing order of their weight
        Arrays.sort(edge);

        // Allocate memory for creating V sets
        DisjointSet disjointSet = new DisjointSet(V);

        i = 0; // Index used to pick next edge

        // Number of edges to be taken is equal to V-1
        while (e < V - 1) {
            // Step 2: Pick the smallest edge and increment the index for next iteration
            Edge next_edge = edge[i++];

            int x = disjointSet.find(next_edge.src);
            int y = disjointSet.find(next_edge.dest);

            // If including this edge does not cause cycle, include it in result
            if (x != y) {
                result[e++] = next_edge;
                disjointSet.union(x, y);
            }
            // Else discard the next_edge
        }

        // Print the contents of result[] to display the built MST
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
    }

    public static void main(String[] args) {
        int V = 4;  // Number of vertices in graph
        int E = 5;  // Number of edges in graph
        KruskalMST graph = new KruskalMST(V, E);

        // add edge 0-1
        graph.edge[0].src = 0;
        graph.edge[0].dest = 1;
        graph.edge[0].weight = 10;

        // add edge 0-2
        graph.edge[1].src = 0;
        graph.edge[1].dest = 2;
        graph.edge[1].weight = 6;

        // add edge 0-3
        graph.edge[2].src = 0;
        graph.edge[2].dest = 3;
        graph.edge[2].weight = 5;

        // add edge 1-3
        graph.edge[3].src = 1;
        graph.edge[3].dest = 3;
        graph.edge[3].weight = 15;

        // add edge 2-3
        graph.edge[4].src = 2;
        graph.edge[4].dest = 3;
        graph.edge[4].weight = 4;

        graph.kruskalMST();
    }
}
