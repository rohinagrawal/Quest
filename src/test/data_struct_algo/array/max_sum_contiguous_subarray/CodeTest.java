package array.max_sum_contiguous_subarray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void returnsTenForAscendingPrefixBeforeBigDrop() {
        assertEquals(10, code.maxSubArray(new int[]{1, 2, 3, 4, -10}));
    }

    @Test
    void returnsSixForMixedSignArray() {
        assertEquals(6, code.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
