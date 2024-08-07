package array.max_abs_distance;

public class Code {
    public int maxArr(int[] A) {
        /**
         * Max( |A[i] - A[j]| + |i - j| )
         * A[i] - A[j] + i - j = (A[i] + i) - (A[j] + j)
         * A[i] - A[j] - i + j = (A[i] - i) - (A[j] - j)
         * - A[i] + A[j] + i - j = (A[j] - j) - (A[i] - i)
         * - A[i] + A[j] - i + j = (A[j] + j) - (A[i] + j)
         */
        int maxSum = Integer.MIN_VALUE;
        int maxDiff = Integer.MIN_VALUE;
        int minSum = Integer.MAX_VALUE;
        int minDiff = Integer.MAX_VALUE;
        for (int i = 0; i < A.length; i++) {
            maxSum = Math.max(maxSum, A[i] + i);
            maxDiff = Math.max(maxDiff, A[i] - i);
            minSum = Math.min(minSum, A[i] + i);
            minDiff = Math.min(minDiff, A[i] - i);

        }
        return Math.max(maxSum - minSum, maxDiff - minDiff);
    }
}
