package splitwise;

public class PaymentNode {
    SplitWiseUser from;
    SplitWiseUser to;
    Integer amount;

    public SplitWiseUser getFrom() {
        return from;
    }

    public void setFrom(SplitWiseUser from) {
        this.from = from;
    }

    public SplitWiseUser getTo() {
        return to;
    }

    public void setTo(SplitWiseUser to) {
        this.to = to;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
