package array.spiral_order_matrix_2;

public class Code {
    /**
     * [
     *  1,  2,  3,  4
     *  12, 13, 14, 5
     *  11, 16, 15, 6
     *  10, 9,  8,  7
     * ]
     * @param A
     * @return
     */
    public int[][] generateMatrix(int A) {
        int [][] matrix = new int[A][A];
        int count = 1;
        int x_low = 0;
        int x_high = A - 1;
        int y_low = 0;
        int y_high = A - 1;
        while (count <= A*A && x_low <= x_high && y_low <= y_high) {
            for (int i = x_low; i <= x_high; i++) {
                matrix[y_low][i] = count++;
            }
            y_low++;
            for (int j = y_low; j <= y_high; j++) {
                matrix[j][x_high] = count++;
            }
            x_high--;
            for (int i = x_high; i >= x_low; i--) {
                matrix[y_high][i] = count++;
            }
            y_high--;
            for (int j = y_high; j >= y_low; j--) {
                matrix[j][x_low] = count++;
            }
            x_low++;
        }
        return matrix;
    }
}
