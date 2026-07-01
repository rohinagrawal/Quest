import<bits/stdc++.h>

using namespace std;

/**
* Your Code object will be instantiated and called as such:
* Code* obj = new Code();
* obj->push(val);
* obj->pop();
* int param_3 = obj->top();
* int param_4 = obj->getMin();
*/

class Code {
public:
    stack<long long> s;
    long long min;
    Code() {
        s = {};
        min = INT_MAX;
    }
    void push(int val) {
        if(s.empty()){
            min = val;
            s.push(val);
        } else {
            if (min>val){
                s.push(2*(long long)val - min);
                min = val;
            } else{
                s.push(val);
            }
        }
    }
    void pop() {
        if(!s.empty()){
            if (s.top()<min){
                min = 2* min - s.top();
                s.pop();
            } else{
                s.pop();
                if(s.empty()){
                    min = INT_MAX;
                }
            }
        }
    }
    int top() {
        if(s.empty()){
            return INT_MAX;
        } else {
            if (s.top()<min){
                return min;
            } else{
                return s.top();
            }
        }
    }
    int getMin() {
        if(s.empty()){
            return INT_MAX;
        } else {
            return min;
        }
    }
};
