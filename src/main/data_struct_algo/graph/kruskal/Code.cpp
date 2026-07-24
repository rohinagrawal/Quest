#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    long long kruskal(int n, vector<vector<int>> adj) {
        vector<int> parent(n, -1);
        vector<int> size(n, 1);
        auto comparator = [](const vector<int> &a, const vector<int> &b) {
            return a[2] < b[2];
        };
        sort(adj.begin(), adj.end(), comparator);// min
        long long weight = 0;
        for (vector<int> edge : adj) {
            if (unionNodes(edge[0], edge[1], parent, size)) {
                weight +=edge[2];
            }
        }
        int root = findRoot(0, parent);
        for (int i = 1; i<n; ++i) {
            if (findRoot(i, parent)!=root) {
                return -1;
            }
        }
        return weight;
    }

    int findRoot(int node, vector<int> &parent) {
        if (parent[node] == -1) {
            return node;
        } else {
            int temp = findRoot(parent[node], parent);
            parent[node] = temp;
            return temp;
        }
    }

    bool unionNodes(int nodeA, int nodeB, vector<int> &parent, vector<int> &size) {
        int rootA = findRoot(nodeA, parent);
        int rootB = findRoot(nodeB, parent);
        if (rootA == rootB) {
            return false;
        }
        if (size[rootA] < size[rootB]) {
            swap(rootA, rootB);
        }
        parent[rootB] = rootA;
        size[rootA]+=size[rootB];
        return true;
    }
};