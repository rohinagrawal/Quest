package array.set_matrix_zero;

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
    void zeroesRowAndColumnForSingleZero() {
        ArrayList<ArrayList<Integer>> matrix = toMatrix(new int[][]{
                {1, 0, 1},
                {1, 1, 1},
                {1, 1, 1}
        });

        code.setZeroes(matrix);

        assertEquals(toMatrix(new int[][]{
                {0, 0, 0},
                {1, 0, 1},
                {1, 0, 1}
        }), matrix);
    }

    @Test
    void zeroesRowsAndColumnForTwoZeroesInSameColumn() {
        ArrayList<ArrayList<Integer>> matrix = toMatrix(new int[][]{
                {1, 0, 1},
                {1, 1, 1},
                {1, 0, 1}
        });

        code.setZeroes(matrix);

        assertEquals(toMatrix(new int[][]{
                {0, 0, 0},
                {1, 0, 1},
                {0, 0, 0}
        }), matrix);
    }
}
