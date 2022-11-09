import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;

    private String lastName;

    private String uuid;

    private byte pinHash[];

    private ArrayList<Account> accounts;


    public User( String firstName, String lastName, String pin, Bank theBank) throws NoSuchAlgorithmException {

        // get user details
        this.firstName = firstName;
        this.lastName = lastName;

        //hashing the pin using MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        this.pinHash = md.digest(pin.getBytes());

        // get a new unique universal ID
        this.uuid = theBank.getNewUserUUID();

        // create an empty list of accounts
        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
    }


    // adding an account to the user
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    // return uuid
    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String aPin){

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);

        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName() {
        return this.firstName;
    }

    // print summary for the user account
    public void printAccountsSummary(){
        System.out.printf("\n\n%s's accounts summary\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++){
            System.out.printf("%d) %s\n", a+1,  this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    // get the number of accounts of the user and return the number of accounts
    public int numAccounts() {
        return this.accounts.size();
    }

    // print the transaction history for a particular account
    public void printAcctTransHistory(int theAcct) {
        this.accounts.get(theAcct).printTransHistory();
    }


    // get the balance of a particular account
    public double getAccountBalance(int acct) {
        return this.accounts.get(acct).getBalance();
    }

    // get the UUID of a particular account
    public String getAccUUID(int accId){
        return this.accounts.get(accId).getUUID();
    }

    public void addAccTransaction(int acc, double amount, String memo) {
        this.accounts.get(acc).addTransaction(amount, memo);
    }
}
