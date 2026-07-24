#include <bits/stdc++.h>

using namespace std;

class Code {
public:

    //parent = {-1, -1, -1, -1, -1, ...}
    //size = {1, 1, 1, 1, 1, 1, ...}

    void dsu(int n, vector<vector<int>> queries) {
        int components = n;
        vector<int> parent(n, -1);
        vector<int> size(n, 1);
        for (vector<int> query: queries) {
            if (query[0] == 0) {
                if (unionNodes(query[1], query[2], parent, size)) {
                    components--;
                }
            } else {
                std::cout << components;
            }
        }
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
        int rootA = findRoot(nodeA);
        int rootB = findRoot(nodeB);
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