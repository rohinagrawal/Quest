#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    struct Edge{
        int vertex;
        int weight;
    };

    int smallestCostPath(vector<vector<Edge>> &adj, int startVertex, int endVertex, int maxWeight) {
        int maxCost = maxWeight * (adj.size()-1);
        vector<bool> visited(adj.size(), false);
        vector<queue<int>> buckets (maxCost+1, {});
        buckets[0].push(startVertex);
        for (int i=0; i<buckets.size(); ++i) {
            while (!buckets[i].empty()) {
                int temp = buckets[i].front();
                buckets[i].pop();
                if (!visited[temp]){
                    visited[temp] = true;
                    if (temp == endVertex) {
                        return i;
                    }
                    for (int j=0; j<adj[temp].size(); ++j) {
                        if (!visited[adj[temp][j].vertex]) {
                            buckets[i+adj[temp][j].weight].push(adj[temp][j].vertex);
                        }
                    }
                }
            }
        }
        return -1;
    }
};