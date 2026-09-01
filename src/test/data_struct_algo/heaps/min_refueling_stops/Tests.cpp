#include "../../../../main/data_struct_algo/heaps/min_refueling_stops/Code.cpp"
#include <cassert>
#include <iostream>

int main() {
    Code sol;

    // Example 1: A = 1, B = 1, C = [], D = [] -> 0 stops
    {
        vector<vector<int>> stations = {};
        assert(sol.minRefuelStops(1, 1, stations) == 0);
    }

    // Example 2: A = 100, B = 1, C = [10], D = [100] -> unreachable -> -1
    {
        vector<vector<int>> stations = {{10, 100}};
        assert(sol.minRefuelStops(100, 1, stations) == -1);
    }

    // Example 3: A = 100, B = 10, C = [10,20,30,60], D = [60,30,30,40] -> 2 stops
    {
        vector<vector<int>> stations = {{10, 60}, {20, 30}, {30, 30}, {60, 40}};
        assert(sol.minRefuelStops(100, 10, stations) == 2);
    }

    std::cout << "All tests passed." << std::endl;
    return 0;
}
