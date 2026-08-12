#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    bool checkBipartite(vector<vector<int>> &adj) {
        vector<int> colour(adj.size(), 0);
        queue<int> bfs;
        for (int i = 0; i<adj.size(); ++i) {
            if (colour[i]==0) {
                colour[i] = 1;
                bfs.push(i);
                while (!bfs.empty()) {
                    int temp = bfs.front();
                    bfs.pop();
                    int nextColour = colour[temp]==1 ? 2 : 1;
                    for (int j = 0; j<adj[temp].size(); ++j) {
                        if (colour[adj[temp][j]] == colour[temp]) {
                            return false;
                        } else if (colour[adj[temp][j]] == 0) {
                            colour[adj[temp][j]] = nextColour;
                            bfs.push(adj[temp][j]);
                        }
                    }
                }
            }
        }
        return true;
    }
};