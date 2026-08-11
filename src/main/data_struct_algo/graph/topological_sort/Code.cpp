#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    vector<int> topologicalOrder(vector<vector<int>> &adj) {
        vector<int> order;
        vector<int> inDegree(adj.size(), 0);
        for (int i = 0; i<adj.size(); ++i) {
            for (int j = 0; j<adj[i].size(); ++j) {
                inDegree[adj[i][j]]++;
            }
        }
        queue<int> dfs;
        for (int i=0; i<inDegree.size(); ++i) {
            if (inDegree[i]==0) {
                dfs.push(i);
            }
        }
        while (!dfs.empty()) {
            int temp = dfs.front();
            dfs.pop();
            order.push_back(temp);
            for (int j = 0; j<adj[temp].size(); ++j) {
                inDegree[adj[temp][j]]--;
                if (inDegree[adj[temp][j]]==0) {
                    dfs.push(adj[temp][j]);
                }
            }
        }
        return order;
    }
};