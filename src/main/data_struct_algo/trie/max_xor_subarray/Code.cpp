#include <bits/stdc++.h>

using namespace std;

int bits = 31;

class Code {
public:
    struct TrieNode {
        TrieNode* child[2];

        TrieNode() {
            this->child[0] = NULL;
            this->child[1] = NULL;
        }
    };

    int maxSubarray(vector<int>& nums) {
        int prefixXor = 0;
        int maxx = INT_MIN;
        TrieNode* root = new TrieNode();
        insert(root, 0);
        for (int i = 0; i<nums.size(); i++) {
            if (i==0) {
                prefixXor=nums[i];
            } else {
                prefixXor=prefixXor^nums[i];
            }
            maxx = max(maxx, search(root, prefixXor));
            insert(root, prefixXor);
        }
        return maxx;
    }

    int search(TrieNode* root, int num) {
        TrieNode* node = root;
        int maxx = 0;
        for (int i = 0; i<bits; i++) {
            int bit = (num>>(bits-1-i)) & 1;
            int maxxBit;
            if (node->child[1-bit] != NULL) {
                maxxBit = 1;
                node = node->child[1-bit];
            } else {
                maxxBit = 0;
                node = node->child[bit];
            }
            maxx = maxx | (maxxBit<<(bits-1-i));
        }
        return maxx;
    }

    void insert(TrieNode* root, int num) {
        TrieNode* node = root;
        for (int i = 0; i<bits; i++) {
            int bit = (num>>(bits-1-i)) & 1;
            if (node->child[bit] == NULL) {
                node->child[bit] = new TrieNode();
            }
            node = node->child[bit];
        }
    }

};