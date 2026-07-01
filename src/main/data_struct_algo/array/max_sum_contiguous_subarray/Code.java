package array.max_sum_contiguous_subarray;

public class Code {
    public int maxSubArray(final int[] A) {
        int sum = Integer.MIN_VALUE;
        int currentSum = 0;
        for (int i = 0; i < A.length; i++) {
            currentSum += A[i];
            sum = Math.max(currentSum, sum);
            if (currentSum < 0) {
                currentSum = 0;
            }
        }
        return sum;
    }
}
