#include <bits/stdc++.h>

using namespace std;

class Code {
public:

    priority_queue<int, vector<int>> firstHalf;
    priority_queue<int, vector<int>, greater<int>> secondHalf;

    vector<double> streamMedian(vector<int> nums) {
        vector<double> ans;
        for (int num : nums) {
            insert(num);
            ans.push_back(query());
        }
        return ans;
    }

    double query() {
        int diff = firstHalf.size() - secondHalf.size();
        if (diff == -1) {
            return secondHalf.top();
        } else if (diff == 1) {
            return firstHalf.top();
        } else {
            return (firstHalf.top() + secondHalf.top())/2.0;
        }
    }

    void insert(int num) {
        if (firstHalf.size()==0) {
            firstHalf.push(num);
        } else {
            if (firstHalf.top()>=num) {
                insertInHalf(num, "first");
            } else {
                insertInHalf(num, "second");
            }
        }
    }

    void insertInHalf(int num, string half) {
        if (half == "first") {
            if (firstHalf.size() - secondHalf.size() == 1) {
                firstHalf.push(num);
                secondHalf.push(firstHalf.top());
                firstHalf.pop();
            } else {
                firstHalf.push(num);
            }
        } else {
            if (secondHalf.size() - firstHalf.size() == 1) {
                secondHalf.push(num);
                firstHalf.push(secondHalf.top());
                secondHalf.pop();
            } else {
                secondHalf.push(num);
            }
        }
    }
};