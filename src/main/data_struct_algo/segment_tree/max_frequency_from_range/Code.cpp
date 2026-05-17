include<bits/stdc++.h>

using namespace std;

class Code {
public:

    struct TreeNode {
        int leftElement;
        int leftElementFreq;
        int rightElement;
        int rightElementFreq;
        int maxFreq;

        TreeNode() {
            this->leftElement = 0;
            this->leftElementFreq = -1;
            this->rightElement = 0;
            this->rightElementFreq = -1;
            this->maxFreq = -1;
        }
        TreeNode(int leftElement, int leftElementFreq, int rightElement, int rightElementFreq, int maxFreq) {
            this->leftElement = leftElement;
            this->leftElementFreq = leftElementFreq;
            this->rightElement = rightElement;
            this->rightElementFreq = rightElementFreq;
            this->maxFreq = maxFreq;
        }
        bool isDummy() {
            return
            this->leftElement == 0 &&
            this->leftElementFreq == -1 &&
            this->rightElement == 0 &&
            this->rightElementFreq == -1 &&
            this->maxFreq == -1;

        }
    };

    void maxFrequency(vector<int> arr, vector<vector<int>> queries) {
        vector<TreeNode> segmentTree(4*arr.size(), TreeNode());
        //memset(segmentTree, NULL, 4*arr.size());
        create(arr, segmentTree, 0, arr.size()-1, 0);
        for (int i = 0; i<queries.size(); i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            cout<<query(segmentTree, l, r, 0, arr.size()-1, 0).maxFreq<<endl;
        }
    }

    TreeNode merge(TreeNode leftNode, TreeNode rightNode) {

        if (leftNode.isDummy() && rightNode.isDummy()){
            return TreeNode();
        } else if (leftNode.isDummy()) {
            return rightNode;
        } else if (rightNode.isDummy()){
            return leftNode;
        } else {
            int newLeftFreq = leftNode.leftElementFreq;
            int newRightFreq = rightNode.rightElementFreq;
            int newMaxFreq = max(leftNode.maxFreq, rightNode.maxFreq);

            // If the boundary elements match, we can bridge them
            if (leftNode.rightElement == rightNode.leftElement) {
                newMaxFreq = max(leftNode.rightElementFreq+rightNode.leftElementFreq, newMaxFreq);

                // If left child is completely uniform, its frequency extends into the right child
                if (leftNode.leftElement == rightNode.leftElement) {
                    newLeftFreq = leftNode.leftElementFreq + rightNode.leftElementFreq;
                }

                // If right child is completely uniform, its frequency extends back into the left child
                if (leftNode.rightElement == rightNode.rightElement) {
                    newRightFreq = leftNode.rightElementFreq + rightNode.rightElementFreq;
                }
            }
            return TreeNode(leftNode.leftElement, newLeftFreq, rightNode.rightElement, newRightFreq, newMaxFreq);
        }
    }

    void create(vector<int> &arr, vector<TreeNode> &segmentTree, int rangeStart, int rangeEnd, int treeIndex) {
        if (rangeStart == rangeEnd) {
            segmentTree[treeIndex] = TreeNode(arr[rangeStart], 1, arr[rangeEnd], 1, 1);
            return;
        }
        int mid = (rangeEnd + rangeStart) / 2 ;
        create(arr, segmentTree, rangeStart, mid, (treeIndex*2)+1);
        create(arr, segmentTree, mid + 1, rangeEnd, (treeIndex*2)+2);
        TreeNode leftNode = segmentTree[(treeIndex*2)+1];
        TreeNode rightNode = segmentTree[(treeIndex*2)+2];
        segmentTree[treeIndex] = merge(leftNode, rightNode);
        return;
    }

    TreeNode query(vector<TreeNode> &segmentTree, int queryStart, int queryEnd, int rangeStart, int rangeEnd, int treeIndex) {
        if (queryEnd < rangeStart || queryStart > rangeEnd) {
            // Range Completely Outside Query
            return TreeNode();
        } else if (rangeStart >= queryStart && rangeEnd <= queryEnd) {
            // Range Completely Inside Query
            return segmentTree[treeIndex];
        } else {
            // Range Partially inside Query
            int mid = (rangeEnd + rangeStart) / 2 ;
            TreeNode leftNode = query(segmentTree, queryStart, queryEnd, rangeStart, mid, (treeIndex*2)+1);
            TreeNode rightNode = query(segmentTree, queryStart, queryEnd, mid+1, rangeEnd, (treeIndex*2)+2);
            return merge(leftNode, rightNode);
        }
    }

};