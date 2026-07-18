#include <bits/stdc++.h>

using namespace std;

class Code {
public:

    vector<int> dijikstras_heap(vector<vector<pair<int,int>>> &adj, int source) {
        vector<int> distance (adj.size(), INT_MAX);
        vector<bool> visited(adj.size(), false);
        auto comp = [](const pair<int,int> &a, const pair<int,int> &b) {
            return a.second > b.second;
        };
        priority_queue<pair<int, int>, vector<pair<int, int>>, decltype(comp)> pq(comp);
        pq.push({source, 0});
        while (!pq.empty()) {
            pair<int, int> temp = pq.top();
            pq.pop();
            if (!visited[temp.first]) {
                distance[temp.first] = temp.second;
                for (int i = 0; i< adj[temp.first].size(); ++i) {
                    if (!visited[adj[temp.first][i].first]) {
                        pq.push({adj[temp.first][i].first, temp.second+adj[temp.first][i].second});
                    }
                }
                visited[temp.first] = true;
            }
        }
        return distance;
    }

    vector<int> dijikstras_set(vector<vector<int>> &adj, int source) {

    }
};