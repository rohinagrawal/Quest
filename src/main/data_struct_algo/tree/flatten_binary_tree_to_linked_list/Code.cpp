import<bits/stdc++.h>

using namespace std;

/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Code {
public:
    struct FlatList{
        TreeNode* start;
        TreeNode* end;
        FlatList() : start(nullptr), end(nullptr) {}
    };

    void flatten(TreeNode* root) {
        root = flattenNode(root).start;
    }

    FlatList flattenNode(TreeNode* root){
        FlatList list;
        if(root == NULL){
            return list;
        }

        FlatList left = flattenNode(root->left);
        FlatList right = flattenNode(root->right);

        root->left = NULL;
        list.start = root;
        list.end = root;

        if(left.start != NULL && left.end != NULL){
            list.end->right = left.start;
            list.end = left.end;
        }

        if(right.start != NULL && right.end != NULL){
            list.end->right = right.start;
            list.end = right.end;
        }

        return list;
    }
};