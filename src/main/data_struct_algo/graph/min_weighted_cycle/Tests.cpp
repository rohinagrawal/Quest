#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: n = 4
    // edges = [ {0,1,1}, {1,2,2}, {2,0,3}, {2,3,1}, {3,1,1} ] (directed {u, v, w})
    vector<vector<pair<int,int>>> adj1(4);
    adj1[0] = {{1, 1}};
    adj1[1] = {{2, 2}};
    adj1[2] = {{0, 3}, {3, 1}};
    adj1[3] = {{1, 1}};

    assert(sol.minCycle(adj1) == 4);
    assert(sol.minCycle_optimized(adj1) == 4);

    // Example 2: n = 3, edges = [ {0,1,2}, {1,2,3} ] -> DAG, no cycle -> -1
    vector<vector<pair<int,int>>> adj2(3);
    adj2[0] = {{1, 2}};
    adj2[1] = {{2, 3}};
    adj2[2] = {};

    assert(sol.minCycle(adj2) == -1);
    assert(sol.minCycle_optimized(adj2) == -1);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
