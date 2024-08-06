package dsa.array.search_sorted_matrix;

import java.util.ArrayList;

public class Code {
    public int solve(ArrayList<ArrayList<Integer>> A, int B) {
        int i= 0;
        int j= A.get(i).size()-1;
        while (i>=0 && i<A.size() && j>=0 && j< A.get(i).size()){
            if (A.get(i).get(j) == B && (j<=0 || A.get(i).get(j-1) < B)){
                return (i+1) * 1009 + (j+1);
            }
            else if (A.get(i).get(j) >= B){
                j--;
            }
            else {
                i++;
            }
        }
        return -1;
    }
}
