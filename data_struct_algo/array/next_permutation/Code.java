package array.next_permutation;

public class Code {

    public long findNextPermutation(long number){
        if (number < 0){
            return 0;
        }
        int digits = (int) Math.floor(Math.log10(number)+1);
        int [] numberArray = new int[digits];
        for (int i = digits-1; i >= 0; i--){
            numberArray[i] = (int) (number % 10);
            number = number / 10;
        }
        numberArray = nextPermutation(numberArray);
        number = numberArray[0];
        for (int i = 1; i < digits; i++){
            number *= 10;
            number += numberArray[i];
        }
        return number;
    }

    public int[] nextPermutation(int[] A) {
        return A;
    }
}