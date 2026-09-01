package array.spiral_order_matrix_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void generatesTwoByTwoSpiral() {
        int[][] expected = {
                {1, 2},
                {4, 3}
        };
        assertArrayEquals(expected, code.generateMatrix(2));
    }

    @Test
    void generatesFiveByFiveSpiral() {
        int[][] expected = {
                {1, 2, 3, 4, 5},
                {16, 17, 18, 19, 6},
                {15, 24, 25, 20, 7},
                {14, 23, 22, 21, 8},
                {13, 12, 11, 10, 9}
        };
        assertArrayEquals(expected, code.generateMatrix(5));
    }
}
