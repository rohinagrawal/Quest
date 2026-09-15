#include "../../../../main/data_struct_algo/stack/count_subarray_first_element_minimum/Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: arr = [1, 2, 1] -> 5
    {
        vector<int> arr = {1, 2, 1};
        assert(sol.countSubArray(arr) == 5);
    }

    // Example 2: arr = [1, 3, 5, 2] -> 8
    {
        vector<int> arr = {1, 3, 5, 2};
        assert(sol.countSubArray(arr) == 8);
    }

    std::cout << "All tests passed." << std::endl;
    return 0;
}
