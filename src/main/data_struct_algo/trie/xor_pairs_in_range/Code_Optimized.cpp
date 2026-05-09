import<bits/stdc++.h>

using namespace std;

int trie[400000][2];
int count_trie[400000];
int node_count;
int bits = 15;

class Code {
public:

    int countPairs(vector<int>& nums, int low, int high) {
        reset();
        int ans = 0;
        insert(nums[0]);
        for(int i = 1; i<nums.size(); i++){
            ans+=(searchLessThan(nums[i], high+1)-searchLessThan(nums[i], low));
            insert(nums[i]);
        }
        return ans;
    }

    int searchLessThan(int value, int high){
        int count = 0;
        int node = 0;
        for(int i = 0; i<bits; i++){
            if(node==-1){
                break;
            }
            int bitV = (value >> (bits-1-i)) & 1;
            int bitH = (high >> (bits-1-i)) & 1;

            if(bitH == 1){
                count+=trie[node][bitV] !=0 ? count_trie[trie[node][bitV]] : 0;

                node = trie[node][1-bitV] !=0 ? trie[node][1-bitV] : -1;
            } else {
                node = trie[node][bitV] !=0 ? trie[node][bitV] : -1;
            }
        }
        return count;
    }

    void insert(int value){
        int node = 0;
        for(int i = 0; i<bits; i++){
            int bit = (value >> (bits-1-i)) & 1;
            if(trie[node][bit]==0){
                trie[node][bit] = ++node_count;
            }
            count_trie[trie[node][bit]]++;
            node = trie[node][bit];
        }
    }

    void reset(){
        node_count = 0;
        memset(count_trie, 0, sizeof(count_trie));
        memset(trie, 0, sizeof(trie));
    }

};