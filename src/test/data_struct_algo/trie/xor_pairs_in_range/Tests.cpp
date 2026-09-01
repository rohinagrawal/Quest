#include "../../../../main/data_struct_algo/trie/xor_pairs_in_range/Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: nums = [1,4,2,7], low = 2, high = 6 -> 6 nice pairs
    {
        vector<int> nums = {1, 4, 2, 7};
        assert(sol.countPairs(nums, 2, 6) == 6);
    }

    // Example 2: nums = [9,8,4,2,1], low = 5, high = 14 -> 8 nice pairs
    {
        vector<int> nums = {9, 8, 4, 2, 1};
        assert(sol.countPairs(nums, 5, 14) == 8);
    }

    std::cout << "All tests passed." << std::endl;
    return 0;
}
