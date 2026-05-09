import<bits/stdc++.h>

using namespace std;

class Code {
public:
    struct TrieNode{
        int bit;
        TrieNode* child[2];
        int count;

        TrieNode(int bit){
            this->bit = bit;
            this->child[0] = NULL;
            this->child[1] = NULL;
            this->count = 0;
        }
    };

    int countPairs(vector<int>& nums, int low, int high) {
        int bits = 15;
        int count = 0;
        vector<int> l = convert2Bin(low, bits);
        vector<int> nh = convert2Bin(high+1, bits);
        TrieNode* root = new TrieNode(-1);
        insert(root, convert2Bin(nums[0], bits));
        for(int i=1; i<nums.size(); i++){
            vector<int> target = convert2Bin(nums[i], bits);
            count+=(searchLessThan(root, nh, target)-searchLessThan(root, l, target));
            insert(root, target);
        }
        return count;
    }

    vector<int> convert2Bin(int x, int bits){
        vector<int> bin;
        for(int i = bits-1; i>=0; i--){
            bin.push_back((x>>i)&1);
        }
        return bin;
    }

    void insert(TrieNode* root, vector<int> num){
        TrieNode* node = root;
        node->count++;
        for(int i = 0; i<num.size(); i++){
            if(node->child[num[i]] == NULL){
                node->child[num[i]] = new TrieNode(num[i]);
            }
            node->child[num[i]]->count++;
            node = node->child[num[i]];
        }
    }

    int searchLessThan(TrieNode* root, vector<int> &h, vector<int> &curr){
        /*
            once 2 is returned from both or the length is reached return count of that node
        */
        TrieNode* trie = root;
        int count = 0;
        TrieNode* nextTrie = NULL;
        for(int i = 0; i<curr.size(); i++){
            if(trie ==NULL) break;
            nextTrie = NULL;
            for(int j = 0; j<=1 ; j++){
                if(trie->child[j] == NULL){
                    continue;
                }
                int status = lessThan(h[i], j, curr[i]);
                if(status == 0){
                    continue;
                } else if (status == 1){
                    nextTrie = trie->child[j];
                } else {
                    count+= trie->child[j]->count;
                }
            }
            trie = nextTrie;
        }
        return count;
    }

    int lessThan(int highBit, int trie, int curr){
        /*
            return ->
            0 Not Accpeted
            1 Partiallay Accepted
            2 Accepted
        */
        if(highBit == 1){
            if(trie == 1){
                if(curr == 1){
                    return 2;
                } else {
                    return 1;
                }
            } else {
                if(curr == 1){
                    return 1;
                } else {
                    return 2;
                }
            }
        } else {
            if(trie == 1){
                if(curr == 1){
                    return 1;
                } else {
                    return 0;
                }
            } else {
                if(curr == 1){
                    return 0;
                } else {
                    return 1;
                }
            }
        }
    }

    int moreThan(int lowBit, int trie, int curr){
        /*
            return ->
            0 Not Accpeted
            1 Partiallay Accepted
            2 Accepted
        */
        if(lowBit == 1){
            if(trie == 1){
                if(curr == 1){
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if(curr == 1){
                    return 1;
                } else {
                    return 0;
                }
            }
        } else {
            if(trie == 1){
                if(curr == 1){
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if(curr == 1){
                    return 2;
                } else {
                    return 1;
                }
            }
        }
    }
};