#include "../../../../main/data_struct_algo/graph/dijikstras/Code.cpp"
#include <cassert>
#include <iostream>

// Only dijikstras_heap() is tested here. dijikstras_set() has an empty,
// unimplemented body and is intentionally not exercised.

int main() {
    Code sol;

    // Example 1: n = 5, source = 0
    // edges = [ {0,1,4}, {0,2,1}, {2,1,2}, {1,3,1}, {2,3,5}, {3,4,3} ]
    vector<vector<pair<int,int>>> adj1(5);
    adj1[0] = {{1, 4}, {2, 1}};
    adj1[1] = {{3, 1}};
    adj1[2] = {{1, 2}, {3, 5}};
    adj1[3] = {{4, 3}};
    adj1[4] = {};

    vector<int> dist1 = sol.dijikstras_heap(adj1, 0);
    vector<int> expected1 = {0, 3, 1, 4, 7};
    assert(dist1 == expected1);

    // Example 2: n = 4, source = 0
    // edges = [ {0,1,2}, {1,2,3} ]; vertex 3 unreachable.
    // The problem statement allows either -1 or INF (repo convention) for
    // unreachable vertices; this implementation leaves the initial INT_MAX
    // sentinel untouched for unreached vertices.
    vector<vector<pair<int,int>>> adj2(4);
    adj2[0] = {{1, 2}};
    adj2[1] = {{2, 3}};
    adj2[2] = {};
    adj2[3] = {};

    vector<int> dist2 = sol.dijikstras_heap(adj2, 0);
    assert(dist2[0] == 0);
    assert(dist2[1] == 2);
    assert(dist2[2] == 5);
    assert(dist2[3] == INT_MAX);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
