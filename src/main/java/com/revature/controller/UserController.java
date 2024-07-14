package com.revature.controller;

import com.revature.entity.checkingAccount;
import com.revature.exception.LoginFail;
import com.revature.service.AccountService;
import com.revature.service.UserService;
import com.revature.entity.User;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class UserController {

    private Scanner scanner;
    private UserService userService;
    private AccountService accountService;

    /*
        The controller takes in a scanner and service object
            - scanner is defined outside the class and passed in, so we can
              control closing the scanner when we are done in an easier way
              (try with resources)
            - userService gives the controller access to the service layer, which will
              handle enforcing our business and software requirements
     */
    public UserController(Scanner scanner, UserService userService, AccountService accountService){
        this.scanner = scanner;
        this.userService = userService;
        this.accountService = accountService;
    }

    /*
        this promptUserForService method is our entrypoint to the bank application:
        it currently contains code to handle registering an account or exiting the
        app, but this can be refactored to handle more options (like logging). It
        also can be refactored to call helper methods to control the flow of the application
        in a more organized and readable way
     */
    public void promptUserForService(Map<String,String> controlMap){
        // user needs to prompt they want to make an account
        System.out.println("What would you like to do?");
        System.out.println("1. Register an account");
        System.out.println("2. Login");
        System.out.println("q. Quit");
        try{
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    // If the user provides correct credentials their username is saved in the controlMap
                    // to be used in the main method for facilitating getting the correct bank account information
                    controlMap.put("User", login().getUsername());
                    while(Boolean.parseBoolean(controlMap.get("logout"))){
                        openCheckingAccount(controlMap);
                    }
                    break;
                case "q":
                    System.out.println("Goodbye!");
                    // set the loopApplication boolean to false to exit the while loop in the main method
                    controlMap.put("Continue Loop", "false");
            }
        // this exception triggers if the user enters invalid credentials
        } catch(LoginFail exception){
            System.out.println(exception.getMessage());
        }
    }

    public void registerNewUser(){
        // this either returns details on the new account or returns a failure message
        // TODO: generic runtime exception is thrown, make it more specific
        User newCredentials = getUserCredentials();
        User newUser = userService.validateNewCredentials(newCredentials);
        System.out.printf("New account created: %s", newUser);
    }

    public User login(){
        // we can re-use getUserCredentials() here to avoid rewriting the same logic
        return userService.checkLoginCredentials(getUserCredentials());
    }

    public User getUserCredentials(){
        String newUsername;
        String newPassword;
        // user needs to provide a username and password
        System.out.print("Please enter a username: ");
        newUsername = scanner.nextLine();
        System.out.print("Please enter a password: ");
        newPassword = scanner.nextLine();
        return new User(newUsername, newPassword);
    }

    public void openCheckingAccount(Map<String,String> controlMap){
        System.out.printf("Banking stuff for %s can happen here! Press any key to continue. \n\n", controlMap.get("User"));

        System.out.println("What would you like to do?\n ");
        System.out.println("1. Open checking account");
        System.out.println("2. Close checking account");
        System.out.println("3. View checking account");
        System.out.println("4. Withdraw ");
        System.out.println("5. Deposit ");
        System.out.println("q. Logout ");

        try{
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    accountService.makeAccount(controlMap.get("User"));
                    break;
                case "2":
                    accountService.deleteAccount(controlMap.get("User"));
                    //controlMap.put("User", login().getUsername());
                    break;
                case "3":
                    checkingAccount accountInfo =  accountService.checkBalance(controlMap.get("User"));
                    System.out.printf("\nAccount Username: %s\n", accountInfo.getUsername());
                    System.out.printf("Account Balance: $%s\n\n", accountInfo.getBalance());
                    break;
                case "4":
                    System.out.println("How much money would you like to withdraw?\n ");

                    String with = scanner.nextLine();

                    checkingAccount accInfo = accountService.withdrawMoney(controlMap.get("User"), Integer.parseInt(with));

                    System.out.printf("Balance: $%s\n\n", accInfo.getBalance());

                    break;
                case "5":
                    System.out.println("How much money would you like to deposit?\n ");

                    String depo = scanner.nextLine();

                    checkingAccount acc = accountService.depositMoney(controlMap.get("User"), Integer.parseInt(depo));

                    System.out.printf("Balance: $%s\n\n", acc.getBalance());

                    break;
                case "q":
                    System.out.println("Goodbye! Have a nice day!\n");
                    controlMap.remove("User");
                    // set the loopApplication boolean to false to exit the while loop in the main method
                    controlMap.put("logout", "false");
            }


            // this exception triggers if the user enters invalid credentials
        } catch(LoginFail exception){
            System.out.println(exception.getMessage()) ;
        }
    }

}
