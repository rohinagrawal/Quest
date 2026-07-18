#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    vector<vector<int>> floydWarshall(vector<vector<pair<int,int>>> &adj) {
        vector<vector<int>> distance(adj.size(), vector<int>(adj.size(), INT_MAX));

        for (int i = 0; i<adj.size(); ++i) {
            distance[i][i] = 0;
        }

        for (int i = 0; i<adj.size(); ++i) {
            for (int j = 0; j<adj[i].size(); ++j) {
                distance[i][adj[i][j].first] = adj[i][j].second;
            }
        }

        for (int k=0; k<distance.size(); ++k) {
            for (int i = 0; i<distance.size(); ++i) {
                for (int j = 0; j<distance[i].size(); ++j) {
                    if (distance[i][k] != INT_MAX && distance[k][j] != INT_MAX) {
                        distance[i][j] = min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }
        }
        return distance;
    }
};