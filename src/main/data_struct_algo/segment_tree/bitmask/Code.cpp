include <bits/stdc++.h>

using namespace std;

class Code {
public:
    static const int MOD = 1000000007;

    /**
     * @param n          length of the bitmask (starts as all zeros)
     * @param operations each row is [type, l, r]; type 1 flips [l, r], type 2 counts ones in [l, r]
     * @return           sum of all type-2 query results modulo MOD
     */
    int sumSetBitQueries(int n, const vector<vector<int>>& operations) {
        vector<int> bitmask(n, 0);
        vector<int> segmentTree(4*n, 0);
        create(bitmask, segmentTree, 0, bitmask.size()-1, 0);
        int sum = 0;
        for (int i = 0; i<operations.size(); i++) {
            if (operations[i][0] == 1) {
                //Update
                for (int j = operations[i][1]; j <= operations[i][2]; j++) {
                    update(bitmask, j, 1-bitmask[j], segmentTree, 0, bitmask.size()-1, 0);
                }
            } else {
                //Query
                sum+=(query(operations[i][1], operations[i][2], segmentTree, 0, bitmask.size()-1, 0)%MOD);
                sum%=MOD;
            }
        }
        return sum;
    }

    void create(vector<int> &arr, vector<int> &segmentTree, int rangeStart, int rangeEnd, int treeIndex) {
        if (rangeStart == rangeEnd) {
            segmentTree[treeIndex] = arr[rangeStart];
            return;
        }
        int mid = (rangeEnd + rangeStart)/2;
        create(arr, segmentTree, rangeStart, mid, 2*treeIndex+1);
        create(arr, segmentTree, mid+1, rangeEnd, 2*treeIndex+2);
        segmentTree[treeIndex] = merge(segmentTree[2*treeIndex+1], segmentTree[2*treeIndex+2]);
        return;
    }

    int merge(int leftNode, int rightNode) {
        return leftNode + rightNode;
    }

    int query(int queryLeft, int queryRight, vector<int> &segmentTree, int rangeStart, int rangeEnd, int treeIndex) {
        if (rangeEnd < queryLeft || rangeStart > queryRight) {
            return 0;
        } else if (queryLeft <= rangeStart && queryRight >= rangeEnd) {
            return segmentTree[treeIndex];
        } else {
            int mid = (rangeEnd + rangeStart)/2;
            int left = query(queryLeft, queryRight, segmentTree, rangeStart, mid, 2*treeIndex+1);
            int right = query(queryLeft, queryRight, segmentTree, mid+1, rangeEnd, 2*treeIndex+2);
            return merge(left, right);
        }
    }

    void update(vector<int> &arr, int updateIndex, int updateValue, vector<int> &segmentTree, int rangeStart, int rangeEnd, int treeIndex) {
        if (rangeStart == rangeEnd) {
            segmentTree[treeIndex] = updateValue;
            arr[updateIndex] = updateValue;
            return;
        }
        int mid = (rangeEnd + rangeStart)/2;
        if (updateIndex<=mid) {
            update(arr, updateIndex, updateValue, segmentTree, rangeStart, mid, 2*treeIndex+1);
        } else {
            update(arr, updateIndex, updateValue, segmentTree, mid+1, rangeEnd, 2*treeIndex+2);
        }
        segmentTree[treeIndex] = merge(segmentTree[2*treeIndex+1], segmentTree[2*treeIndex+2]);
        return;
    }
};