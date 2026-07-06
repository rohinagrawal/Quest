package splitwise;

public class Balance {
    SplitWiseUser splitWiseUser;
    Integer amount;

    public SplitWiseUser getUser() {
        return splitWiseUser;
    }

    public void setUser(SplitWiseUser splitWiseUser) {
        this.splitWiseUser = splitWiseUser;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
