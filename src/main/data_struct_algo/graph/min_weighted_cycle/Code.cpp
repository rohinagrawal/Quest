#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    int minCycle(vector<vector<pair<int, int>> > &adj) {
        int n = adj.size();
        vector<int> minCycle(n, INT_MAX);
        auto comp = [](const pair<int,int> &a, const pair<int,int> &b) {
            return a.second>b.second;
        };
        for (int i=0; i<n; ++i) {
            priority_queue<pair<int,int>, vector<pair<int,int>>, decltype(comp)> pq(comp);
            vector<bool> visited(n, false);
            for (int j=0; j<adj[i].size(); ++j) {
                pq.push(adj[i][j]);
            }
            while (!pq.empty()) {
                pair<int, int> temp = pq.top();
                pq.pop();
                if (!visited[temp.first]) {
                    if (temp.first == i) {
                        minCycle[i] = min(minCycle[i], temp.second);
                        break;
                    }
                    for (int j=0; j<adj[temp.first].size(); ++j) {
                        if (!visited[adj[temp.first][j].first]) {
                            pq.push({adj[temp.first][j].first, temp.second + adj[temp.first][j].second});
                        }
                    }
                    visited[temp.first] = true;
                }
            }
        }
        int minn = INT_MAX;
        for (int i=0; i<minCycle.size(); ++i) {
            minn = min(minn,minCycle[i]);
        }
        if (minn == INT_MAX) {
            return -1;
        } else {
            return minn;
        }
    }
};