package splitwise;

import java.util.*;

public class SplitWiseService {

    private Map<Integer, Expense> expenseMap;

    private EqualSplitService equalSplitService;

    private ExactSplitService exactSplitService;

    public SplitWiseService() {
        this.expenseMap = new HashMap<>();
        equalSplitService = new EqualSplitService();
        exactSplitService = new ExactSplitService();
    }

    public void addExpense(int expenseNo, int amount, SplitWiseUser splitWiseUserPaid, List<SplitWiseUser> usersInExpense, String splitType, Map<SplitWiseUser,Integer> exactSplitMap){
        if (amount<=0){
            System.out.println("Expense Cannot be added with -ve Value");
            return;
        }
        Expense expense;
        switch (splitType){
            case "EXACT":
                expense = exactSplitService.addExpense(expenseNo,amount, splitWiseUserPaid,exactSplitMap);
                break;
            case "EQUAL":
                expense = equalSplitService.addExpense(expenseNo,amount, splitWiseUserPaid,usersInExpense);
                break;
            default:
                System.out.println("Invalid Split Type");
                return;
        }
        this.expenseMap.put(expense.getId(),expense);
        System.out.println("Expense Added");
    }

    public Map<SplitWiseUser,Integer> getBalances(){
        Map<SplitWiseUser,Integer> userBalanceMap = new HashMap<>();
        expenseMap.values().forEach(expense -> expense.getUserBalances().forEach((key, value) -> {
            if (userBalanceMap.containsKey(key)) {
                userBalanceMap.put(key, userBalanceMap.get(key) + value);
            } else {
                userBalanceMap.put(key, value);
            }
        }));
        return userBalanceMap;
    }

    public void printBalances(Map<SplitWiseUser, Integer> userBalances){
        System.out.println("User\t\tBalance");
        userBalances.forEach((user, balance) -> System.out.println(user.getId()+"\t\t\t"+balance));
    }

    public List<PaymentNode> getPaymentGraph(Map<SplitWiseUser,Integer> userBalanceMap){
        PriorityQueue<Balance> giveBalanceMaxHeap = new PriorityQueue<>(Comparator.comparingInt(Balance::getAmount));
        PriorityQueue<Balance> takeBalanceMaxHeap = new PriorityQueue<>((o1, o2) -> Integer.compare(o2.getAmount(), o1.getAmount()));
        userBalanceMap.forEach(((user, amount) -> {
            if (amount < 0){
                Balance balance = new Balance();
                balance.setUser(user);
                balance.setAmount(amount);
                giveBalanceMaxHeap.add(balance);
            } else if (amount > 0) {
                Balance balance = new Balance();
                balance.setUser(user);
                balance.setAmount(amount);
                takeBalanceMaxHeap.add(balance);
            }
        }));
        List<PaymentNode> paymentGraph = new ArrayList<>();
        while (!giveBalanceMaxHeap.isEmpty() && !takeBalanceMaxHeap.isEmpty()){
            Balance give = giveBalanceMaxHeap.poll();
            Balance take = takeBalanceMaxHeap.poll();
            Balance temp;
            PaymentNode transaction = new PaymentNode();
            transaction.setFrom(give.getUser());
            transaction.setTo(take.getUser());
            transaction.setAmount(Math.min(Math.abs(give.getAmount()),Math.abs(take.getAmount())));
            paymentGraph.add(transaction);
            if (give.getAmount() + take.getAmount() >0){
                temp = new Balance();
                temp.setAmount(give.getAmount() + take.getAmount());
                temp.setUser(take.getUser());
                takeBalanceMaxHeap.add(temp);
            } else if (give.getAmount() + take.getAmount() <0){
                temp = new Balance();
                temp.setAmount(give.getAmount() + take.getAmount());
                temp.setUser(give.getUser());
                giveBalanceMaxHeap.add(temp);
            }
        }
        return paymentGraph;
    }

    public void printPaymentGraph(List<PaymentNode> paymentGraph){
        System.out.println("From\tTo\t\t\tAmount");
        paymentGraph.forEach(paymentNode -> System.out.println(paymentNode.getFrom().getId()+"\t\t"+paymentNode.getTo().getId()+"\t\t\t"+paymentNode.getAmount()));
    }
}
