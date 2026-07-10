#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    vector<int> dfs(vector<vector<int>>& adj, int vertex) {
        vector<int> answer;
        vector<bool> visited (adj.size(),false);
        stack<int> dfsStack;
        dfsStack.push(vertex);
        while (!dfsStack.empty()) {
            int temp = dfsStack.top();
            dfsStack.pop();
            if (!visited[temp]) {
                visited[temp] = true;
                for (int i=0; i<adj[temp].size();i++) {
                    if (!visited[adj[temp][i]]) {
                        dfsStack.push(adj[temp][i]);
                    }
                }
                answer.push_back(temp);
            }
        }
        return answer;
    }

    vector<int> bfs(vector<vector<int>>& adj, int vertex) {
        vector<int> answer;
        vector<bool> visited (adj.size(),false);
        queue<pair<int, int>> bfsQueue;
        bfsQueue.push({vertex, 0});
        visited[vertex] = true;
        while (!bfsQueue.empty()) {
            pair<int, int> temp = bfsQueue.front();
            bfsQueue.pop();
            for (int i=0; i<adj[temp.first].size(); i++) {
                if (!visited[adj[temp.first][i]]) {
                    bfsQueue.push({adj[temp.first][i], temp.second+1});
                    visited[adj[temp.first][i]] = true;
                }
            }
            answer.push_back(temp.first);
        }
        return answer;
    }
};