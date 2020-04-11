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
            weight[i] = 999999;
            parent[i] = -2;
        }
        int[] discovered = new int[size];
        int startNode = -1;

        for (int i = 0; i < size; i++) {
            if (this.adj_matrix.get(i).get(i) == -1) {
                startNode = i;
                break;
            }
        }

        int currentNode = startNode;
        parent[currentNode] = -1;
        discovered[currentNode] = 1;

        ArrayList<Integer> adjNodes = this.adj_matrix.get(currentNode);
        PriorityQueue<Integer> unexplored = new PriorityQueue<>(Comparator.comparingInt(node -> weight[node]));

        for (int i = 0; i < size; i++) {
            int cost = adjNodes.get(i);
            if (cost != -1) {
                weight[i] = cost;
                unexplored.add(i);
                parent[i] = currentNode;
                discovered[i] = 1;
            }
        }

        while (!unexplored.isEmpty()) {
            currentNode = unexplored.poll();
            adjNodes = this.adj_matrix.get(currentNode);

            for (int i = 0; i < size; i++) {

                if (i == startNode) {
                    continue;
                }

                int cost = adjNodes.get(i);

                if (discovered[i] == 0) {
                    if (cost != -1) {
                        weight[i] = cost;
                        parent[i] = currentNode;
                        unexplored.add(i);
                        discovered[i] = 1;
                    }
                } else {
                    if (cost == -1) {
                        continue;
                    }
                    if (cost < weight[i]) {
                        weight[i] = cost;
                        parent[i] = currentNode;
                    }
                }
            }
        }

        return parent;
    }
}
