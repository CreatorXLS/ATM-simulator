import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ATM {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        Scanner sc = new Scanner(System.in);

        // initialize Bank
        Bank theBank = new Bank("Bank of Mizzou");

        // add user , create a saving account
        User user = theBank.addUser("Sizou", "Menzo", "1234");

        // add a checking account for the user
        Account newAccount = new Account("Cheque", user, theBank);
        user.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true){
            // stay in login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, sc);

            // stay in menu until user quits
            ATM.printUserMenu(curUser, sc);
        }

    }
    public static User mainMenuPrompt(Bank theBank, Scanner sc){
        // initialize
        String userID;
        String pin;
        User authUser;

        // prompt the user for id and pin until correct details entered
        do{
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();


            // get user that matches userID and pin
            authUser = theBank.userLogin(userID, pin);
            if(authUser == null){
                System.out.println("Incorrect user ID or pin, "+ "Please try again.");
            }

        }while(authUser == null); // continues looping until successful login

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc){

        // print a summary of the user account
        theUser.printAccountsSummary();


        // initialize
        int choice;

        // user menu
        do{
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");

            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            }

        }while(choice < 1 || choice > 5);

        switch (choice){
            case 1:
                ATM.showTransactionHistory(theUser, sc);
                break;
            case 2:
                ATM.doWithdrawal(theUser, sc);
                break;
            case 3:
                ATM.depositFunds(theUser, sc);
                break;
            case 4:
                ATM.transferFunds(theUser, sc);
        }

        // redisplay the menu unless user quits
        if(choice != 5){
            ATM.printUserMenu(theUser, sc);
        }

    }

    // show transaction history of an account
    public static void showTransactionHistory(User theUser, Scanner sc){
        int theAcct;
    // get the account whose history to view
        do {
            System.out.printf("Enter the number (1-%d) of the account \n" + "whose transactions you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct>= theUser.numAccounts()){
                System.out.println("invalid account. Please try again.");
            }

        } while(theAcct < 0 || theAcct>= theUser.numAccounts());

        // print the account transaction history
        theUser.printAcctTransHistory(theAcct);
    }


    // transferring of funds
    public static void transferFunds(User theUser, Scanner sc){

        // initialize
        int fromAcc;
        int toAcc;
        double amount;
        double accBal;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ", theUser.numAccounts());
            fromAcc = sc.nextInt()-1;
            if (fromAcc < 0 || fromAcc >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBal = theUser.getAccountBalance(fromAcc);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer to: ", theUser.numAccounts());
            toAcc = sc.nextInt()-1;
            if (toAcc < 0 || fromAcc >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcc < 0 || toAcc >= theUser.numAccounts());

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $(%.02f): $", accBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            } else if(amount > accBal){
                System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", accBal);
            }
        } while (amount < 0 || amount > accBal);

        theUser.addAccTransaction(fromAcc, -1*amount, String.format("Transfer to account %s", theUser.getAccUUID(toAcc)));
        theUser.addAccTransaction(toAcc, amount, String.format("Transfer to account %s", theUser.getAccUUID(fromAcc)));

    }

    public static void doWithdrawal(User theUser, Scanner sc){
        // initialize
        int fromAcc;
        double amount;
        double accBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to withdraw from: ", theUser.numAccounts());
            fromAcc = sc.nextInt()-1;
            if (fromAcc < 0 || fromAcc >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcc < 0 || fromAcc >= theUser.numAccounts());

        accBal = theUser.getAccountBalance(fromAcc);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to withdraw (max $(%.02f): $", accBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            } else if(amount > accBal){
                System.out.printf("Amount must not be greater than\n" + "balance of $%.02f.\n", accBal);
            }
        } while (amount < 0 || amount > accBal);

        sc.nextLine();

        // get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdrawal
        theUser.addAccTransaction(fromAcc, -1*amount, memo);
    }

    // proceed to deposit money
    public static void depositFunds(User theUser, Scanner sc){
        // initialize
        int toAcc;
        double amount;
        double accBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" + "to deposit in: ", theUser.numAccounts());
            toAcc = sc.nextInt()-1;
            if (toAcc < 0 || toAcc >= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcc < 0 || toAcc >= theUser.numAccounts());

        accBal = theUser.getAccountBalance(toAcc);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $(%.02f): $", accBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Amount must be greater than 0");
            }
        } while (amount < 0 );

        sc.nextLine();

        // get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdrawal
        theUser.addAccTransaction(toAcc, amount, memo);
    }

}
