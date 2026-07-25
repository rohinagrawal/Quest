#include <bits/stdc++.h>

using namespace std;

class Code {
public:

    // A is 1-indexed in meaning: node i (1..n) points to A[i-1].
    // Answer = number of cycles NOT containing node 1
    //        = number of DSU components that do not contain node 1,
    //          after unioning every edge EXCEPT node 1's own out-edge.
    int goodGraph(vector<int> A) {
        int n = A.size();
        vector<int> parent(n + 1, -1);
        vector<int> size(n + 1, 1);

        // Skip node 1's out-edge: node 1 is good regardless of where it points.
        for (int i = 2; i <= n; ++i) {
            unionNodes(i, A[i - 1], parent, size);
        }

        int rootOne = findRoot(1, parent);
        unordered_set<int> badRoots;
        for (int i = 1; i <= n; ++i) {
            int r = findRoot(i, parent);
            if (r != rootOne) {
                badRoots.insert(r);
            }
        }
        return (int) badRoots.size();
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
        size[rootA] += size[rootB];
        return true;
    }
};
