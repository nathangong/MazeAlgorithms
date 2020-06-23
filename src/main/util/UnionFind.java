package main.util;

public class UnionFind {
    int[] nodes;
    int[] sizes;

    UnionFind(int n) {
        nodes = new int[n];
        sizes = new int[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = i;
            sizes[i] = 1;
        }
    }

    void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (sizes[i] < sizes[j]) {
            nodes[i] = nodes[j];
            sizes[j] += sizes[i];
        }
        else {
            nodes[j] = nodes[i];
            sizes[i] += sizes[j];
        }
    }

    int root(int index) {
        while (nodes[index] != index) {
            nodes[index] = nodes[nodes[index]];
            index = nodes[index];
        }
        return index;
    }

    boolean connected(int p, int q) {
        return root(p) == root(q);
    }
}