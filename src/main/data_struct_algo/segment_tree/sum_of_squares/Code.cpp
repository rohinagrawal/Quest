#include <bits/stdc++.h>

using namespace std;

class Code {
public:

	struct TreeNode {
		int squareSum;
		int sum;

		TreeNode() {
			this->squareSum = 0;
			this->sum = 0;
		}
	};

	struct QueryLazyNode {
		int queryType;
		int queryValue;

		QueryLazyNode() {
			this->queryType = 0;
			this->queryValue = 0;
		}
	};

	vector<int> sumOfSquares(vector<int> &arr, vector<vector<int>> &queries) {
		vector<TreeNode> segmentTree(4*arr.size(), TreeNode());
		vector<QueryLazyNode> lazyTree(4*arr.size(), QueryLazyNode());
		vector<int> ans;
		create(segmentTree, lazyTree, 0, arr.size()-1, 0, arr);
		for (int i = 0; i<queries.size(); i++) {
			if (queries[i][0] == 1) {
				ans.push_back(query(segmentTree, lazyTree, 0, arr.size()-1, 0, queries[i][1], queries[i][2]));
			} else {
				update(segmentTree, lazyTree, 0, arr.size()-1, 0, queries[i][0], queries[i][1], queries[i][2], queries[i][3]);
			}
		}
		return ans;
	}

	void create(vector<TreeNode> &segmentTree, vector<QueryLazyNode> &lazyTree, int rangeStart, int rangeEnd, int treeIndex, vector<int> &arr) {
		if (rangeStart == rangeEnd) {
			segmentTree[treeIndex].sum = arr[rangeStart];
			segmentTree[treeIndex].squareSum = arr[rangeStart]*arr[rangeStart];
			lazyTree[treeIndex].queryType = 0;
			lazyTree[treeIndex].queryValue = 0;
			return;
		}
		int mid = (rangeStart + rangeEnd)/2;
		create(segmentTree, lazyTree, rangeStart, mid, 2*treeIndex+1, arr);
		create(segmentTree, lazyTree, mid+1, rangeEnd, 2*treeIndex+2, arr);
		segmentTree[treeIndex].sum = segmentTree[2*treeIndex+1].sum + segmentTree[2*treeIndex+2].sum;
		segmentTree[treeIndex].squareSum = segmentTree[2*treeIndex+1].squareSum + segmentTree[2*treeIndex+2].squareSum;
		return;
	}

	void update(vector<TreeNode> &segmentTree, vector<QueryLazyNode> &lazyTree, int rangeStart, int rangeEnd, int treeIndex, int queryType, int queryLeft, int queryRight, int queryValue) {
		if (lazyTree[treeIndex].queryType > 1) {
			lazyUpdate(segmentTree, lazyTree, rangeStart, rangeEnd, treeIndex, lazyTree[treeIndex].queryType, lazyTree[treeIndex].queryValue);
			lazyTree[treeIndex].queryType = 0;
			lazyTree[treeIndex].queryValue = 0;
		}
		if (rangeStart > queryRight || rangeEnd < queryLeft) {
			// Completely Outside
			return;
		} else if (rangeStart >= queryLeft && rangeEnd <= queryRight) {
			// Completly Inside
			lazyUpdate(segmentTree, lazyTree, rangeStart, rangeEnd, treeIndex, queryType, queryValue);
			lazyTree[treeIndex].queryType = 0;
			lazyTree[treeIndex].queryValue = 0;
		} else {
			int mid = (rangeStart + rangeEnd)/2;
			update(segmentTree, lazyTree, rangeStart, mid, 2*treeIndex+1, queryType, queryLeft, queryRight, queryValue);
			update(segmentTree, lazyTree, mid+1, rangeEnd, 2*treeIndex+2, queryType, queryLeft, queryRight, queryValue);
			segmentTree[treeIndex].sum = segmentTree[2*treeIndex+1].sum + segmentTree[2*treeIndex+2].sum;
			segmentTree[treeIndex].squareSum = segmentTree[2*treeIndex+1].squareSum + segmentTree[2*treeIndex+2].squareSum;
		}
	}

	void lazyUpdate(vector<TreeNode> &segmentTree, vector<QueryLazyNode> &lazyTree, int rangeStart, int rangeEnd, int treeIndex, int queryType, int queryValue) {
		if (queryType == 2) {
			segmentTree[treeIndex].squareSum = (rangeEnd - rangeStart + 1)*(queryValue*queryValue);
			segmentTree[treeIndex].sum = (rangeEnd - rangeStart + 1)*queryValue;
		} else if (queryType == 3) {
			segmentTree[treeIndex].squareSum += (rangeEnd - rangeStart + 1)*(queryValue*queryValue) + 2*queryValue*segmentTree[treeIndex].sum;
			segmentTree[treeIndex].sum += (rangeEnd - rangeStart + 1)*queryValue;
		}
		if (rangeStart!=rangeEnd) {
			// This OverWrites the Earlier Present Operation, Whereas er should keep all the previous operations and execute all once update for the Node is needed.
			lazyTree[2*treeIndex+1].queryType = queryType;
			lazyTree[2*treeIndex+1].queryValue = queryValue;
			lazyTree[2*treeIndex+2].queryType = queryType;
			lazyTree[2*treeIndex+2].queryValue = queryValue;
		}
	}

	int query(vector<TreeNode> &segmentTree, vector<QueryLazyNode> &lazyTree, int rangeStart, int rangeEnd, int treeIndex, int queryLeft, int queryRight) {
		if (lazyTree[treeIndex].queryType > 1) {
			lazyUpdate(segmentTree, lazyTree, rangeStart, rangeEnd, treeIndex, lazyTree[treeIndex].queryType, lazyTree[treeIndex].queryValue);
			lazyTree[treeIndex].queryType = 0;
			lazyTree[treeIndex].queryValue = 0;
		}
		if (rangeStart > queryRight || rangeEnd < queryLeft) {
			// Completely Outside
			return 0;
		} else if (rangeStart >= queryLeft && rangeEnd <= queryRight) {
			// Completly Inside
			return segmentTree[treeIndex].squareSum;
		} else {
			int mid = (rangeStart + rangeEnd)/2;
			return query(segmentTree, lazyTree, rangeStart, mid, 2*treeIndex+1, queryLeft, queryRight) +
				query(segmentTree, lazyTree, mid+1, rangeEnd, 2*treeIndex+2, queryLeft, queryRight);
		}
	}

};