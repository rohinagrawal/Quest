package array.continuous_sum_query;

import java.util.Arrays;

public class Code {
    public int[] solve(int A, int[][] B) {
        int[] res = new int[A];
        Arrays.fill(res, 0);
        for (int i = 0; i < B.length; i++) {
            res[B[i][0]-1] += B[i][2];
            if (B[i][1] < res.length)
                res[B[i][1]] -= B[i][2];
        }
        for (int i = 1; i < res.length; i++) {
            res[i] += res[i - 1];
        }
        return res;
    }
}
