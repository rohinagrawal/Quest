import<bits/stdc++.h>

using namespace std;

class Code {
public:
    int countSubArray(vector<int>& array) {
        int count = 0;
        stack<int>s;
        s.push(0);
        for(int i=1; i<array.size();i++){
            if(array[i]>=array[s.top()]){
                s.push(i);
            } else{
                while(!s.empty() && array[s.top()]>array[i]){
                    count+=i-s.top();
                    s.pop();
                }
                s.push(i);
            }
        }
        while(!s.empty()){
            count+=array.size()-s.top();
            s.pop();
        }
        return count;
    }
};