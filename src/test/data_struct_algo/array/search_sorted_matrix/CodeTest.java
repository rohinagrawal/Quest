package array.search_sorted_matrix;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTest {

    private final Code code = new Code();

    private static ArrayList<ArrayList<Integer>> toMatrix(int[][] rows) {
        ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
        for (int[] row : rows) {
            List<Integer> boxedRow = Arrays.stream(row).boxed().collect(Collectors.toList());
            matrix.add(new ArrayList<>(boxedRow));
        }
        return matrix;
    }

    @Test
    void findsTargetInThreeByThreeMatrix() {
        ArrayList<ArrayList<Integer>> matrix = toMatrix(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });

        assertEquals(1011, code.solve(matrix, 2));
    }

    @Test
    void returnsSmallestPositionForDuplicateTarget() {
        ArrayList<ArrayList<Integer>> matrix = toMatrix(new int[][]{
                {1, 2},
                {3, 3}
        });

        assertEquals(2019, code.solve(matrix, 3));
    }
}
