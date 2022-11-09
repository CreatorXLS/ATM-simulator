import java.util.Date;

public class Transaction {

    private double amount;

    private Date timestamp;

    // statement
    private String memo;

    // the account in which the transaction was performed
    private Account inAccount;


    public Transaction (double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }
    public Transaction (double amount, String memo, Account inAccount){
        // call to arg constructor
        this(amount, inAccount);

        this.memo = memo;

    }

    public double getAmount() {
        return this.amount;
    }

    // get a string summarizing the transaction
    public String getSummaryLine() {
        if(this.amount >= 0){
            return String.format("%s : $%.02f : %s\n", this.timestamp.toString(), this.amount, this.memo);
        } else{
            return String.format("%s : $(%.02f) : %s\n", this.timestamp.toString(), this.amount, this.memo);
        }
    }
}
