package splitwise;

import java.util.HashMap;
import java.util.Map;

public class ExactSplitService extends SplitService{

    public Expense addExpense(int expenseNo, int amount, SplitWiseUser splitWiseUserPaid, Map<SplitWiseUser,Integer> usersInExpense){
        HashMap<SplitWiseUser,Integer> userBalances = new HashMap<>();
        usersInExpense.forEach((user, exactAmount) -> {
            userBalances.put(user, Utility.negateValue(exactAmount));
        });
        userBalances.put(splitWiseUserPaid,userBalances.get(splitWiseUserPaid)+amount);
        Expense expense = new Expense(expenseNo);
        expense.setAmount(amount);
        expense.setUserBalances(userBalances);
        printSplitBalances(expense);
        return expense;
    }
}
