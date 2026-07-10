#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    int shortestDistance(vector<vector<int>>& adj, int start, int end) {
        vector<bool> visited(adj.size(), false);
        queue<int> bfsQueue;
        bfsQueue.push(start);
        visited[start] = true;
        int level = 0;
        while (!bfsQueue.empty()) {
            int levelNodes = bfsQueue.size();
            for (int i = 0; i< levelNodes; ++i) {
                int temp = bfsQueue.front();
                bfsQueue.pop();
                if (temp == end) {
                    return level;
                }
                for (int j=0; j<adj[temp].size(); ++j) {
                    if (!visited[adj[temp][j]]) {
                        bfsQueue.push(adj[temp][j]);
                        visited[adj[temp][j]] = true;
                    }
                }
            }
            ++level;
        }
        return -1;
    }
};