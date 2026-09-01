#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // arr = [1,2,3,4]
    // ops: 1 0 3 (query) -> 30
    //      3 1 2 2 (add 2 to [1,2]) -> arr = [1,4,5,4]
    //      1 0 3 (query) -> 58
    //      2 0 1 5 (set [0,1]=5) -> arr = [5,5,5,4]
    //      1 0 2 (query) -> 75
    // Output: [30, 58, 75]
    vector<int> arr = {1, 2, 3, 4};
    vector<vector<int>> queries = {
        {1, 0, 3},
        {3, 1, 2, 2},
        {1, 0, 3},
        {2, 0, 1, 5},
        {1, 0, 2}
    };

    vector<int> result = sol.sumOfSquares(arr, queries);
    assert((result == vector<int>{30, 58, 75}));

    std::cout << "All tests passed." << std::endl;
    return 0;
}
