package io.github.marioluan.datastructures.graph.search;

import io.github.marioluan.datastructures.graph.Graph;

/**
 * Implementation of a Connected Components data structure using {@link DepthFirstSearch} algorithm.
 */
public class ConnectedComponents {
    private final Graph graph;
    private int[] id;
    private int count;
    private boolean[] marked;

    /**
     * Builds a cluster of connected components for every source vertex in the {@link Graph graph}.
     *
     * @param graph a given graph
     */
    public ConnectedComponents(Graph graph) {
        this.graph = graph;
        marked = new boolean[graph.V()];
        id = new int[graph.V()];

        search();
    }

    /**
     * Performs a DFS for each [unmarked] vertex in the graph.
     */
    private void search() {
        for (int i = 0; i < graph.V(); i++)
            if (!marked[i]) {
                search(i);
                count++;
            }
    }

    /**
     * Performs a DFS from source vertex s.
     *
     * @param s the source vertex
     */
    // TODO: add time complexity
    private void search(int s) {
        marked[s] = true;
        id[s] = count;

        for (int w : graph.adj(s))
            if (!marked[w])
                search(w);
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns the component's id of the vertex v.
     *
     * @param v a given vertex
     * @return the component's id of the vertex v.
     */
    public int id(int v) {
        return id[v];
    }
}