#include "../../../../main/data_struct_algo/graph/good_graph/Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: A = [1, 2, 1, 2] -> 1
    vector<int> A1 = {1, 2, 1, 2};
    assert(sol.goodGraph(A1) == 1);

    // Example 2: A = [3, 1, 3, 1] -> 1
    vector<int> A2 = {3, 1, 3, 1};
    assert(sol.goodGraph(A2) == 1);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
