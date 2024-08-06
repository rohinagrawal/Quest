package array.rain_water_trapped;

public class Code {
    public int trap(final int[] A) {
        int[] left = new int[A.length];
        int[] right = new int[A.length];
        left[0] = 0;
        right[A.length - 1] = 0;
        int total_water = 0;
        for (int i = 1; i < A.length; i++) {
            left[i] = Math.max(left[i-1], A[i-1]);
            right[A.length - i - 1] = Math.max(right[A.length - i], A[A.length - i]);
        }
        for (int i = 0; i < A.length; i++) {
            total_water += Math.max(Math.min(left[i], right[i]) - A[i], 0);
        }
        return total_water;
    }
}
