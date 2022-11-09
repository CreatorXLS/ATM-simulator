import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;

    // users in the bank
    private ArrayList<User> users;

    // accounts in the bank
    private ArrayList<Account> accounts;

    // constructor for the bank
    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    // generate uuid for the user
    public String getNewUserUUID() {

        String uuid;
        Random rnd = new Random();
        int len = 6;
        boolean nonUnique;

        // loop until we get a unique id
        do{
            uuid = "";
            for(int c = 0; c < len; c++){
                uuid += ((Integer)rnd.nextInt(10)).toString();
            }
            nonUnique = false;
            for(User u : this.users){
                if (uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);

        return uuid;
    }

    public String getNewAccountUUID() {
        String uuid;
        Random rnd = new Random();
        int len = 10;
        boolean nonUnique;

        // loop until we get a unique id
        do{
            uuid = "";
            for(int c = 0; c < len; c++){
                uuid += ((Integer)rnd.nextInt(10)).toString();
            }
            nonUnique = false;
            for(Account a : this.accounts){
                if (uuid.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);

        return uuid;
    }

    // add an account to the bank
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public User addUser(String firstName, String lastName, String pin) throws NoSuchAlgorithmException {
        //creating a new user and add it to list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);


        // create a savings account""
        Account newAccount = new Account("Savings", newUser, this);

        // add account to thebank list
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }
    // get User object associated with a particular userID and pin
    public User userLogin(String userID, String pin){
        for(User u : this.users){
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }

        // if user is not found or pin is incorrect
        return null;
    }

    // get the name of the bank
    public String getName() {
        return this.name;
    }
}
