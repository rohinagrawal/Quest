package splitwise;

import java.util.Map;

public class Expense {

    Integer id;

    public Integer getId() {
        return id;
    }

    Integer amount;

    Map<SplitWiseUser,Integer> userBalances;

    public Expense(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Map<SplitWiseUser, Integer> getUserBalances() {
        return userBalances;
    }

    public void setUserBalances(Map<SplitWiseUser, Integer> userBalances) {
        this.userBalances = userBalances;
    }
}
