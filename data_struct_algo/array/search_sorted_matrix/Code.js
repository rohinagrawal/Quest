
    //param A : integer
    //param B : array of array of integers
    //return a array of integers
       function solve(A, B) {
   
        let result = [...Array(A)].fill(0)

        for(var i = 0;i<B.length;i++){
            let initial = B[i][0]
            let final = B[i][1]
            let amount = B[i][2]
            for(let j=initial-1;j<final;j++){
                result[j] = result[j] + amount
            }

        }
        return result
       }

       solve(5,[[1, 2, 10], [2, 3, 20], [2, 5, 25]])