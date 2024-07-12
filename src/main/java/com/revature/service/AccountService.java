package com.revature.service;

import com.revature.entity.checkingAccount;
import com.revature.repository.AccountDao;

public class AccountService {
    private AccountDao accountDao;


    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void makeAccount(String username){
        checkingAccount newAccount = new checkingAccount(username, 0);
        accountDao.createAccount(newAccount);
    }
}
