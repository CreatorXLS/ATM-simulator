import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Account {

    // type of account
    private String name;

    private String uuid;

    //account holder
    private User holder;

    // statements
    private ArrayList<Transaction> transactions;


    public Account(String name, User holder, @NotNull Bank theBank){

        // set user account and holder
        this.name = name;
        this.holder = holder;

        // get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        this.transactions = new ArrayList<Transaction>();


    }

    public String getUUID() {
        return this.uuid;
     }

    public String getSummaryLine() {

        // get the balance
        double balance = this.getBalance();

        // format the summary line depending on the account balance
        if(balance >= 0){
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }

    }

    // get the balance of this account by adding the amount of the transactions
    public double getBalance(){
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;

    }
    // get transaction history of the account
    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--){
            System.out.printf(this.transactions.get(t).getSummaryLine());
        }

        System.out.println();
    }

    // add a new transaction in this account
    public void addTransaction(double amount, String memo) {
        // create a new transaction object and add it to a list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }
}
