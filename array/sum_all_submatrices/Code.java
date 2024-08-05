package array.sum_all_submatrices;

public class Code {
    public int solve(int[][] A) {
        int sum =0;
        for(int i=0; i<A.length; i++){
            for(int j=0; j<A[0].length; j++){
                sum = sum + (A[i][j]*(i+1)*(j+1)*(A.length-i)*(A[0].length-j));
            }
        }
        return sum;
    }
}
