package array.sum_all_submatrices;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void returnsSixteenForAllOnesMatrix() {
        int[][] a = {
                {1, 1},
                {1, 1}
        };
        assertEquals(16, code.solve(a));
    }

    @Test
    void returnsFortyForTwoByTwoMatrix() {
        int[][] a = {
                {1, 2},
                {3, 4}
        };
        assertEquals(40, code.solve(a));
    }
}
