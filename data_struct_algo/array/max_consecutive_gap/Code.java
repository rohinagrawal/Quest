package array.max_consecutive_gap;

import java.util.Arrays;

public class Code {
    public int maximumGap(final int[] A) {
        if (A == null || A.length < 2) {
            return 0;
        }

        int maxVal = Integer.MIN_VALUE;
        int minVal = Integer.MAX_VALUE;

        for (int i = 0; i < A.length; i++) {
            maxVal = Math.max(maxVal, A[i]);
            minVal = Math.min(minVal, A[i]);
        }

        int gap = (int) Math.ceil((double) (maxVal - minVal) / (A.length - 1));

        int [] minBucket = new int[A.length - 1];
        int [] maxBucket = new int[A.length - 1];
        Arrays.fill(maxBucket, Integer.MIN_VALUE);
        Arrays.fill(minBucket, Integer.MAX_VALUE);

        minBucket[0] = minVal;
        maxBucket[0] = minVal;
        minBucket[minBucket.length-1] = maxVal;
        maxBucket[maxBucket.length-1] = maxVal;

        for (int i = 0; i < A.length; i++) {
            if (A[i] == minVal || A[i] == maxVal) {
                continue;
            }

            int bucketIndex = (A[i] - minVal) / gap;

            maxBucket[bucketIndex] = Math.max(maxBucket[bucketIndex], A[i]);
            minBucket[bucketIndex] = Math.min(minBucket[bucketIndex], A[i]);
        }

        int ans = Integer.MIN_VALUE;
        int previousMax = minVal;
        for (int i = 0; i < minBucket.length && i < maxBucket.length; i++) {
            if (minBucket[i] != Integer.MAX_VALUE) {
                ans = Math.max(ans, minBucket[i] - previousMax);
            }
            if (maxBucket[i] != Integer.MIN_VALUE) {
                previousMax = maxBucket[i];
            }
        }
        return ans;
    }
}