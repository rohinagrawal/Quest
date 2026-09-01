#include "Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: A = 12 -> 12 = 4 + 4 + 4 -> 3 squares
    assert(sol.minNumbers(12) == 3);

    // Example 2: A = 13 -> 13 = 4 + 9 -> 2 squares
    assert(sol.minNumbers(13) == 2);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
