package com.revature.controller;

import com.revature.entity.checkingAccount;
import com.revature.exception.LoginFail;
import com.revature.service.AccountService;
import com.revature.service.UserService;
import com.revature.entity.User;

import java.awt.geom.Arc2D;
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
        while(Objects.equals(controlMap.get("Continue Loop"), "true")){
            System.out.println("What would you like to do?");
            System.out.println("1. Register an account");
            System.out.println("2. Login");
            System.out.println("q. Quit");

            String userActionIndicated = scanner.nextLine();

            if(Objects.equals(userActionIndicated, "1")){

                registerNewUser();

            }
            else if(Objects.equals(userActionIndicated, "2")){
                controlMap.put("logout", "true");
                controlMap.put("User", login().getUsername());
                while(Boolean.parseBoolean(controlMap.get("logout"))){
                    openCheckingAccount(controlMap);
                }
            }
            else if(Objects.equals(userActionIndicated, "q")){
                System.out.println("Goodbye!");

                controlMap.put("Continue Loop", "false");
            }
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

        while(Objects.equals(controlMap.get("logout"), "true")){

            System.out.printf("Banking stuff for %s can happen here! Press any key to continue. \n\n", controlMap.get("User"));

            System.out.println("What would you like to do?\n ");
            System.out.println("1. Open checking account");
            System.out.println("2. Close checking account");
            System.out.println("3. View checking account");
            System.out.println("4. Withdraw ");
            System.out.println("5. Deposit ");
            System.out.println("q. Logout ");

            String userActionIndicated = scanner.nextLine();

            if(Objects.equals(userActionIndicated, "1")){
                //controlMap.put("accountMade", "true");
                accountService.makeAccount(controlMap.get("User"));
            }
            else if(Objects.equals(userActionIndicated, "2")){
                //controlMap.put("accountMade", "false");
                accountService.deleteAccount(controlMap.get("User"));
            }
            else if(Objects.equals(userActionIndicated, "3")){
                //if(Objects.equals(controlMap.get("accountMade"), "true")){
                checkingAccount accountInfo =  accountService.checkBalance(controlMap.get("User"));
                System.out.printf("\nAccount Username: %s\n", accountInfo.getUsername());
                System.out.printf("Account Balance: $%.2f\n\n", accountInfo.getBalance());
                //}
                //else{
                //System.out.println("Checking account not made. Try again\n");
                //}

            }
            else if(Objects.equals(userActionIndicated, "4")){
                System.out.println("How much money would you like to withdraw?\n ");

                String with = scanner.nextLine();

                checkingAccount accInfo = accountService.withdrawMoney(controlMap.get("User"), Double.parseDouble(with));

                System.out.printf("Balance: $%.2f\n\n", accInfo.getBalance());
            }
            else if(Objects.equals(userActionIndicated, "5")){
                System.out.println("How much money would you like to deposit?\n ");

                String depo = scanner.nextLine();

                checkingAccount acc = accountService.depositMoney(controlMap.get("User"), Double.parseDouble(depo));

                System.out.printf("Balance: %.2f\n\n", acc.getBalance());
            }
            else if(Objects.equals(userActionIndicated, "q")){
                System.out.println("Goodbye! Have a nice day!\n");
                controlMap.remove("User");
                // set the loopApplication boolean to false to exit the while loop in the main method
                controlMap.put("logout", "false");
            }
        }
    }

}
