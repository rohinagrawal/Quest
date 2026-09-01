#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: n = 4, edges = [ {0,1,1}, {1,2,2}, {2,3,3}, {0,3,4}, {0,2,5} ] -> MST weight 6
    vector<vector<int>> edges1 = {
        {0, 1, 1},
        {1, 2, 2},
        {2, 3, 3},
        {0, 3, 4},
        {0, 2, 5}
    };
    assert(sol.kruskal(4, edges1) == 6);

    // Example 2: n = 4, edges = [ {0,1,1}, {2,3,2} ] -> disconnected -> -1
    vector<vector<int>> edges2 = {
        {0, 1, 1},
        {2, 3, 2}
    };
    assert(sol.kruskal(4, edges2) == -1);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
