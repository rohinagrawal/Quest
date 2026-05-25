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
        vector<int> lazyTree(4*n, 0);
        create(bitmask, segmentTree, lazyTree, 0, bitmask.size()-1, 0);
        int sum = 0;
        for (int i = 0; i<operations.size(); i++) {
            if (operations[i][0] == 1) {
                //Update
                update(segmentTree, lazyTree, 0, bitmask.size()-1, operations[i][1], operations[i][2], 0);
            } else {
                //Query
                sum+=(query(segmentTree, lazyTree, 0, bitmask.size()-1, operations[i][1], operations[i][2], 0)%MOD);
                sum%=MOD;
            }
        }
        return sum;
    }

    void create(vector<int> &bitmask, vector<int> &segmentTree, vector<int> &lazyTree, int rangeStart, int rangeEnd, int treeIndex) {
        if (rangeStart == rangeEnd) {
            segmentTree[treeIndex] = bitmask[rangeStart];
            lazyTree[treeIndex] = 0;
            return;
        }
        int mid = (rangeStart + rangeEnd) / 2;
        create(bitmask, segmentTree, lazyTree, rangeStart, mid, 2*treeIndex+1);
        create(bitmask, segmentTree, lazyTree, mid+1, rangeEnd, 2*treeIndex+2);
        segmentTree[treeIndex] = segmentTree[2*treeIndex+1] + segmentTree[2*treeIndex+2];
        lazyTree[treeIndex] = 0;
        return;
    }

    void update(vector<int> &segmentTree, vector<int> &lazyTree, int rangeStart, int rangeEnd, int queryStart, int queryEnd, int treeIndex) {
        if (lazyTree[treeIndex] == 1) {
            segmentTree[treeIndex] = (rangeEnd - rangeStart + 1) - segmentTree[treeIndex];
            if (rangeStart != rangeEnd){
                lazyTree[2*treeIndex+1] ^= 1;
                lazyTree[2*treeIndex+2] ^= 1;
            }
            lazyTree[treeIndex] = 0;
        }
        if (rangeEnd < queryStart || rangeStart > queryEnd) {
            // Completely Outside
            return;
        } else if (rangeStart >= queryStart && rangeEnd <=queryEnd) {
            // Completely Inside
            segmentTree[treeIndex] = (rangeEnd - rangeStart + 1) - segmentTree[treeIndex];
            if (rangeStart != rangeEnd){
                lazyTree[2*treeIndex+1] ^= 1;
                lazyTree[2*treeIndex+2] ^= 1;
            }
            lazyTree[treeIndex] = 0;
            return;
        } else {
            // Partially Inside
            int mid = (rangeStart + rangeEnd) / 2;
            update(segmentTree, lazyTree, rangeStart, mid, queryStart, queryEnd, 2*treeIndex+1);
            update(segmentTree, lazyTree, mid+1, rangeEnd, queryStart, queryEnd, 2*treeIndex+2);
            segmentTree[treeIndex] = segmentTree[2*treeIndex+1] + segmentTree[2*treeIndex+2];
            lazyTree[treeIndex] = 0;
            return;
        }
    }

    int query(vector<int> &segmentTree, vector<int> &lazyTree, int rangeStart, int rangeEnd, int queryStart, int queryEnd, int treeIndex) {
        if (lazyTree[treeIndex] == 1) {
            segmentTree[treeIndex] = (rangeEnd - rangeStart + 1) - segmentTree[treeIndex];
            if (rangeStart != rangeEnd){
                lazyTree[2*treeIndex+1] ^= 1;
                lazyTree[2*treeIndex+2] ^= 1;
            }
            lazyTree[treeIndex] = 0;
        }
        if (rangeEnd < queryStart || rangeStart > queryEnd) {
            // Completely Outside
            return 0;
        } else if (rangeStart >= queryStart && rangeEnd <=queryEnd) {
            // Completely Inside
            return segmentTree[treeIndex];
        } else {
            int mid = (rangeStart + rangeEnd) / 2;
            return query(segmentTree, lazyTree, rangeStart, mid, queryStart, queryEnd, 2*treeIndex+1) +
            query(segmentTree, lazyTree, mid+1, rangeEnd, queryStart, queryEnd, 2*treeIndex+2);
        }
    }
};