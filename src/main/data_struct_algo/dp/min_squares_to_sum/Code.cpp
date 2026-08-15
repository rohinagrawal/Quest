#include <bits/stdc++.h>

using namespace std;

class Code {
public:
    int minNumbers(int A) {
        vector<int> dp(A+1, 0);
        for (int i=0; i<=A; ++i) {
            dp[i] = i;
        }
        for (int i = 4; i<=A; ++i) {
            for (int j = 2; j*j<=i; ++j) {
                dp[i] = min(dp[i - j*j]+1, dp[i]);
            }
        }
        return dp[A];
    }
};
