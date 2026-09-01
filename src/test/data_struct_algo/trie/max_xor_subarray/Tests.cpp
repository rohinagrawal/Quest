#include "../../../../main/data_struct_algo/trie/max_xor_subarray/Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: arr = [1,2,3,4] -> max subarray XOR = 7 (subarray [3,4])
    {
        vector<int> arr = {1, 2, 3, 4};
        assert(sol.maxSubarray(arr) == 7);
    }

    // Example 2: arr = [8,1,2,12] -> max subarray XOR = 15 (subarray [1,2,12])
    {
        vector<int> arr = {8, 1, 2, 12};
        assert(sol.maxSubarray(arr) == 15);
    }

    // Example 3: arr = [4,6] -> max subarray XOR = 6 (subarray [6])
    {
        vector<int> arr = {4, 6};
        assert(sol.maxSubarray(arr) == 6);
    }

    std::cout << "All tests passed." << std::endl;
    return 0;
}
