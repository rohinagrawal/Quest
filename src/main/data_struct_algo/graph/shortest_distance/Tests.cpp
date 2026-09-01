#include "Code.cpp"
#include <cassert>
#include <iostream>

// Note: Code.cpp's shortestDistance(adj, start, end) operates on a pre-built
// graph adjacency list (not a raw grid). The examples below convert the
// documented grid examples into an adjacency list over cell index r*C+c,
// omitting edges to/from blocked ('1') cells.

int main() {
    Code sol;

    // Example 1:
    // grid = [ [0,0,0], [1,0,1], [0,0,0] ], start=(0,0), target=(2,2) -> 4
    // Walkable cell indices (r*3+c): 0=(0,0) 1=(0,1) 2=(0,2) 4=(1,1) 6=(2,0) 7=(2,1) 8=(2,2)
    // Blocked: 3=(1,0) 5=(1,2)
    vector<vector<int>> adj1(9);
    adj1[0] = {1};
    adj1[1] = {0, 2, 4};
    adj1[2] = {1};
    adj1[3] = {};
    adj1[4] = {1, 7};
    adj1[5] = {};
    adj1[6] = {7};
    adj1[7] = {6, 8, 4};
    adj1[8] = {7};
    assert(sol.shortestDistance(adj1, 0, 8) == 4);

    // Example 2:
    // grid = [ [0,1,0], [1,1,0], [0,1,0] ], start=(0,0), target=(2,2) -> -1
    // Walkable cell indices: 0=(0,0) 2=(0,2) 5=(1,2) 8=(2,2)
    // Blocked: 1=(0,1) 3=(1,0) 4=(1,1) 7=(2,1)
    vector<vector<int>> adj2(9);
    adj2[0] = {};
    adj2[1] = {};
    adj2[2] = {5};
    adj2[3] = {};
    adj2[4] = {};
    adj2[5] = {2, 8};
    adj2[6] = {};
    adj2[7] = {};
    adj2[8] = {5};
    assert(sol.shortestDistance(adj2, 0, 8) == -1);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
