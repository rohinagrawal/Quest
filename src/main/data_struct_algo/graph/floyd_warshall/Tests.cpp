#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: n = 4
    // edges = [ {0,1,3}, {1,2,-2}, {0,2,5}, {2,3,2}, {1,3,4} ]  (directed {u, v, w})
    vector<vector<pair<int,int>>> adj1(4);
    adj1[0] = {{1, 3}, {2, 5}};
    adj1[1] = {{2, -2}, {3, 4}};
    adj1[2] = {{3, 2}};
    adj1[3] = {};

    vector<vector<int>> dist1 = sol.floydWarshall(adj1);
    const int INF = INT_MAX;
    vector<vector<int>> expected1 = {
        {0,   3,   1,   3},
        {INF, 0,  -2,   0},
        {INF, INF, 0,   2},
        {INF, INF, INF, 0}
    };
    assert(dist1 == expected1);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
