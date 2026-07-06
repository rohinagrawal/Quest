package splitwise;

import java.util.HashMap;
import java.util.List;

public class EqualSplitService extends SplitService {

    public Expense addExpense(int expenseNo, int amount, SplitWiseUser splitWiseUserPaid, List<SplitWiseUser> usersInExpense){
        HashMap<SplitWiseUser,Integer> userBalances = new HashMap<>();
        usersInExpense.forEach(user -> {
            userBalances.put(user, Utility.negateValue(amount/usersInExpense.size()));
        });
        userBalances.put(splitWiseUserPaid,userBalances.get(splitWiseUserPaid)+amount);
        Expense expense = new Expense(expenseNo);
        expense.setAmount(amount);
        expense.setUserBalances(userBalances);
        printSplitBalances(expense);
        return expense;
    }

}
