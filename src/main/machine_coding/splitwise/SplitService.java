package splitwise;

public class SplitService {

    public void printSplitBalances(Expense expense){
        System.out.println("Expense Id : "+expense.getId());
        System.out.println("Expense Amount : "+expense.getAmount());
        System.out.println("User\t\tAmount");
        expense.getUserBalances().forEach((user, amount) -> System.out.println(user.getId()+"\t\t\t"+amount));
    }
}
