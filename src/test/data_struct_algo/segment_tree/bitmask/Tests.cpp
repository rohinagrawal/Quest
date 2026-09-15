#include "../../../../main/data_struct_algo/segment_tree/bitmask/Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: n = 5
    // (1,0,4) flip all -> 11111; (2,0,4) -> 5
    // (1,1,3) flip [1,3] -> 10001; (2,0,4) -> 2
    // total = 5 + 2 = 7
    {
        vector<vector<int>> ops = {
            {1, 0, 4},
            {2, 0, 4},
            {1, 1, 3},
            {2, 0, 4}
        };
        assert(sol.sumSetBitQueries(5, ops) == 7);
    }

    // Example 2: n = 4
    // (1,0,1) flip -> 1100; (1,2,3) flip -> 1111; (2,0,3) -> 4
    {
        vector<vector<int>> ops = {
            {1, 0, 1},
            {1, 2, 3},
            {2, 0, 3}
        };
        assert(sol.sumSetBitQueries(4, ops) == 4);
    }

    // Example 3: n = 3, only an update, no queries -> 0
    {
        vector<vector<int>> ops = {
            {1, 0, 2}
        };
        assert(sol.sumSetBitQueries(3, ops) == 0);
    }

    std::cout << "All tests passed." << std::endl;
    return 0;
}
