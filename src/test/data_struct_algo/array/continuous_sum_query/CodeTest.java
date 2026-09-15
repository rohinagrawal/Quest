package array.continuous_sum_query;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CodeTest {

    private final Code code = new Code();

    @Test
    void appliesThreeRangeDonationsAcrossFiveBeggars() {
        int[][] donations = {{1, 2, 10}, {2, 3, 20}, {2, 5, 25}};
        assertArrayEquals(new int[]{10, 55, 45, 25, 25}, code.solve(5, donations));
    }

    @Test
    void returnsAllZeroesWhenNoDonations() {
        assertArrayEquals(new int[]{0, 0, 0}, code.solve(3, new int[][]{}));
    }
}
