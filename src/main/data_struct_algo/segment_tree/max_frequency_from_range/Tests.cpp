#include "Code.cpp"
#include <cassert>
#include <iostream>
#include <sstream>

int main() {
    Code sol;

    // maxFrequency prints one line per query via cout; capture stdout to check values.

    // Example 1: arr = [2,2,2,3,3,4], queries [0,2],[1,4],[0,5] -> [3,2,3]
    {
        vector<int> arr = {2, 2, 2, 3, 3, 4};
        vector<vector<int>> queries = {{0, 2}, {1, 4}, {0, 5}};

        std::ostringstream captured;
        std::streambuf* oldBuf = std::cout.rdbuf(captured.rdbuf());
        sol.maxFrequency(arr, queries);
        std::cout.rdbuf(oldBuf);

        assert(captured.str() == "3\n2\n3\n");
    }

    // Example 2: arr = [-1,1,1,1,1,2], queries [0,1],[1,4],[0,5] -> [1,4,4]
    {
        vector<int> arr = {-1, 1, 1, 1, 1, 2};
        vector<vector<int>> queries = {{0, 1}, {1, 4}, {0, 5}};

        std::ostringstream captured;
        std::streambuf* oldBuf = std::cout.rdbuf(captured.rdbuf());
        sol.maxFrequency(arr, queries);
        std::cout.rdbuf(oldBuf);

        assert(captured.str() == "1\n4\n4\n");
    }

    std::cout << "All tests passed." << std::endl;
    return 0;
}
