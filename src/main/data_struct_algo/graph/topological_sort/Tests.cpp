#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: n = 4, edges = [ {0,1}, {0,2}, {1,3}, {2,3} ] -> order [0,1,2,3]
    vector<vector<int>> adj1 = {
        {1, 2},
        {3},
        {3},
        {}
    };
    vector<int> order1 = sol.topologicalOrder(adj1);
    vector<int> expected1 = {0, 1, 2, 3};
    assert(order1 == expected1);

    // Example 2: n = 3, edges = [ {0,1}, {1,2}, {2,0} ] -> cycle -> empty order
    vector<vector<int>> adj2 = {
        {1},
        {2},
        {0}
    };
    vector<int> order2 = sol.topologicalOrder(adj2);
    assert(order2.empty());

    std::cout << "All tests passed." << std::endl;
    return 0;
}
