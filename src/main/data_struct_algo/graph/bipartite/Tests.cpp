#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: n = 4, edges = [ {0,1}, {1,2}, {2,3}, {3,0} ] -> bipartite (true)
    vector<vector<int>> adj1 = {
        {1, 3},
        {0, 2},
        {1, 3},
        {2, 0}
    };
    assert(sol.checkBipartite(adj1) == true);

    // Example 2: n = 3, edges = [ {0,1}, {1,2}, {2,0} ] -> odd cycle (false)
    vector<vector<int>> adj2 = {
        {1, 2},
        {0, 2},
        {1, 0}
    };
    assert(sol.checkBipartite(adj2) == false);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
