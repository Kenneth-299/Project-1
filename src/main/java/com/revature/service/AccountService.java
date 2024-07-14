package com.revature.service;

import com.revature.entity.checkingAccount;
import com.revature.repository.AccountDao;

import java.util.List;

public class AccountService {
    private AccountDao accountDao;


    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void makeAccount(String username){
        checkingAccount newAccount = new checkingAccount(username, 0);
        accountDao.createAccount(newAccount);
    }

    public List<checkingAccount> getAccount(String username){
        return accountDao.getChckingAccount(username);
    }

    public void deleteAccount(String username){
        List<checkingAccount> toDelete = getAccount(username);
        accountDao.deleteCheckingAccount(toDelete.get(0));
    }

    public checkingAccount checkBalance(String username){
        List<checkingAccount> bal = getAccount(username);
        return bal.get(0);
    }

    public checkingAccount withdrawMoney(String username, Integer amountWithdraw){
        List<checkingAccount> acc = getAccount(username);
        Integer amountOwned = acc.get(0).getBalance();

        if(amountOwned - amountWithdraw > 0){
            checkingAccount newBal = new checkingAccount(username, amountOwned - amountWithdraw);
            return accountDao.addNewBalance(newBal);
        }
        else{
            return acc.get(0);
        }
    }

    public checkingAccount depositMoney(String username, Integer amountWithdraw){
        List<checkingAccount> acc = getAccount(username);
        Integer amountOwned = acc.get(0).getBalance();

        checkingAccount newBal = new checkingAccount(username, amountOwned + amountWithdraw);
        return accountDao.addNewBalance(newBal);
    }

}
