package array.set_matrix_zero;

import java.util.ArrayList;
import java.util.Collections;

public class Code {
    public void setZeroes(ArrayList<ArrayList<Integer>> a) {
        boolean initialRowZero = false;
        boolean initialColZero = false;
        for (int i = 0; i <a.size(); i++) {
            for (int j = 0; j <a.get(i).size(); j++) {
                if (a.get(i).get(j) == 0){
                    a.get(0).set(j,0);
                    a.get(i).set(0,0);
                    if (i == 0){
                        initialColZero = true;
                    }
                    if (j == 0){
                        initialRowZero = true;
                    }
                }
            }
        }
        for (int i = 1; i <a.size(); i++) {
            if (a.get(i).get(0) == 0){
                Collections.fill(a.get(i), 0);
            }
        }
        for (int j = 1; j <a.get(0).size(); j++) {
            if (a.get(0).get(j) == 0){
                for (int i = 1; i <a.size(); i++){
                    a.get(i).set(j,0);
                }
            }
        }
        if (initialRowZero){
            for (int i = 0; i <a.size(); i++){
                a.get(i).set(0,0);
            }
        }
        if (initialColZero){
            Collections.fill(a.get(0),0);
        }
    }
}
