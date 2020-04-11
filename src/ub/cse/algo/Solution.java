package ub.cse.algo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * For use in CSE 331
 */
public class Solution {

    private ArrayList<ArrayList<Integer>> adj_matrix;

    public Solution(ArrayList<ArrayList<Integer>> adj_matrix) {
        this.adj_matrix = adj_matrix;
    }


    public int[] outputEdges() {
      /*
       * Output the node ids of the smallest weighted path.
       */
        int size = this.adj_matrix.size();
        int[] weight = new int[size];
        int[] parent = new int[size];
        for (int i = 0; i < size; i++) {
            weight[i] = 51;
            parent[i] = -2;
        }
        int[] discovered = new int[size];
        int[] explored = new int[size];

        int currentNode = 0;
        weight[0] = 0;
        discovered[currentNode] = 1;
        explored[currentNode] = 1;
        parent[currentNode] = -1;

        ArrayList<Integer> adjNodes = this.adj_matrix.get(currentNode);
        PriorityQueue<Integer> unexplored = new PriorityQueue<>(Comparator.comparingInt(node -> weight[node]));

        for (int node = 1; node < size; node++) {
            int cost = adjNodes.get(node);
            if (cost != -1) {
                discovered[node] = 1;
                parent[node] = currentNode;
                weight[node] = cost;
                unexplored.add(node);
            }
        }

        while (!unexplored.isEmpty()) {
            // Pick the node with the given smallest weighted edge
            currentNode = unexplored.poll();
            explored[currentNode] = 1;
            adjNodes = this.adj_matrix.get(currentNode);

            for (int node = 1; node < size; node++) {
                // Check if the node is explored
                if (explored[node] != 1) {
                    int cost = adjNodes.get(node);
                    // Check if the node is reachable
                    if (cost != -1) {
                        // if the node is undiscovered
                        if (discovered[node] == 0) {
                            discovered[node] = 1;
                            parent[node] = currentNode;
                            weight[node] = cost;
                            unexplored.add(node);
                        }
                        // if the node is discovered
                        else {
                            // Modify if the weight of the current edge is less than the previous one
                            // Make sure the weight of each edge is the smallest between the given ready-to-explore node set
                            if (cost < weight[node]) {
                                unexplored.remove(node);
                                parent[node] = currentNode;
                                weight[node] = cost;
                                unexplored.add(node);
                            }
                        }
                    }
                }
            }
        }

        return parent;
    }
}
