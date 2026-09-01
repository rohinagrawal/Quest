#include <cassert>
#include <iostream>
#include <vector>

// Definition for a binary tree node (declared here because Code.cpp only
// documents this struct in a comment; it does not define it).
struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

#include "Code.cpp"

int main() {
    Code sol;

    // Example 1: tree rooted at 1 with left child 2 (children 3, 4) and
    // right child 5 (right child 6).
    // Expected flattened order: 1 -> 2 -> 3 -> 4 -> 5 -> 6, all left pointers null.
    TreeNode* n3 = new TreeNode(3);
    TreeNode* n4 = new TreeNode(4);
    TreeNode* n6 = new TreeNode(6);
    TreeNode* n2 = new TreeNode(2, n3, n4);
    TreeNode* n5 = new TreeNode(5, nullptr, n6);
    TreeNode* root = new TreeNode(1, n2, n5);

    sol.flatten(root);

    std::vector<int> expected = {1, 2, 3, 4, 5, 6};
    TreeNode* curr = root;
    for (int v : expected) {
        assert(curr != nullptr);
        assert(curr->val == v);
        assert(curr->left == nullptr);
        curr = curr->right;
    }
    assert(curr == nullptr);

    // Example 2: empty tree -> flatten on nullptr should not crash.
    TreeNode* emptyRoot = nullptr;
    sol.flatten(emptyRoot);
    assert(emptyRoot == nullptr);

    std::cout << "All tests passed." << std::endl;
    return 0;
}
