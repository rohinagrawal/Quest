import<bits/stdc++.h>

using namespace std;

class Code {

    struct TreeNode {
         int val;
         TreeNode *left;
         TreeNode *right;
         TreeNode() : val(0), left(nullptr), right(nullptr) {}
         TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
         TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
    };

public:
    vector<int> inOrderTraversal(TreeNode* root) {
        TreeNode* curr = root;
        vector<int> ans;
        while(cur != NULL) {
            if (curr->left == NULL) {
                ans.push_back(curr->val);
                curr = curr->right;
            } else {
                TreeNode* prev = curr->left;
                while(prev->right != NULL && prev->right != curr){
                    prev = prev->right;
                }
                if(prev->right == NULL){
                    prev->right = curr;
                    curr = curr->left;
                } else {
                    ans.push_back(curr->val);
                    prev->right = NULL;
                    curr = curr->right;
                }
            }
        }
        return ans;
    }

    vector<int> preOrderTraversal(TreeNode* root) {
        TreeNode* curr = root;
        vector<int> ans;
        while(cur != NULL) {
            if (curr->left == NULL) {
                ans.push_back(curr->val);
                curr = curr->right;
            } else {
                TreeNode* prev = curr->left;
                while(prev->right != NULL && prev->right != curr){
                    prev = prev->right;
                }
                if(prev->right == NULL){
                    ans.push_back(curr->val);
                    prev->right = curr;
                    curr = curr->left;
                } else {
                    prev->right = NULL;
                    curr = curr->right;
                }
            }
        }
        return ans;
    }

    vector<int> postOrderTraversal(TreeNode* root) {
        vector<int> ans;
        stack<TreeNode*> s;
        TreeNode* curr = root;
        while(curr != NULL || !s.empty()) {

        }
        return ans;
    }
};