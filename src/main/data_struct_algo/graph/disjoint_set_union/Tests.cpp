#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: n = 5, queries = [ union(0,1), union(1,2), count, union(3,4), count ]
    // query[0] == 0 => union(query[1], query[2]); query[0] != 0 => count
    vector<vector<int>> queries1 = {
        {0, 0, 1},
        {0, 1, 2},
        {1},
        {0, 3, 4},
        {1}
    };
    vector<int> result1 = sol.dsu(5, queries1);
    vector<int> expected1 = {3, 2};
    assert(result1 == expected1);

    // Example 2: n = 3, queries = [ union(0,1), union(0,1), count ] -> redundant union must not double count
    vector<vector<int>> queries2 = {
        {0, 0, 1},
        {0, 0, 1},
        {1}
    };
    vector<int> result2 = sol.dsu(3, queries2);
    vector<int> expected2 = {2};
    assert(result2 == expected2);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
