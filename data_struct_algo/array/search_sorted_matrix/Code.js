
    //param A : integer
    //param B : array of array of integers
    //return a array of integers

    // brute Force
       function solve(A, B) {
   
        let result = [...Array(A)].fill(0)

        for(var i = 0;i<B.length;i++){
            let initial = B[i][0]
            let final   = B[i][1]
            let amount  = B[i][2]
            for(let j=initial-1;j<final;j++){
                result[j] = result[j] + amount
            }

        }
        return result
       }

// Optimised

function solveOpt(A,B){
    let result = Array(A).fill(0)
    for(let i = 0;i<B.length;i++){
        let initial = B[i][0] - 1
        let final   = B[i][1]
        let amount  = B[i][2]
       
            result[initial] = result[initial] + amount
            if (final < A) {
                result[final] -= amount;
            }
    }
    for(i=1;i<result.length;i++){
            result[i] = result [i-1]+ result[i] 
    }

    return result
}


    //   solve(5,[[1, 2, 10], [2, 3, 20], [2, 5, 25]])
       solveOpt(5,[[1, 2, 10], [2, 3, 20], [2, 5, 25]])
