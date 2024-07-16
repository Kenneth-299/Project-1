package com.revature.repository;

import com.revature.entity.checkingAccount;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAccount implements  AccountDao{

    private List<checkingAccount> accounts;
    public InMemoryAccount(){
        accounts = new ArrayList<>();
    }

    @Override
    public checkingAccount createAccount(checkingAccount account) {
        accounts.add(account);
        return account;
    }

    @Override
    public List<checkingAccount> getChckingAccount(String username) {
        return accounts;
    }

    @Override
    public checkingAccount deleteCheckingAccount(checkingAccount account) {
        accounts.remove(account);
        return account;
    }

    @Override
    public checkingAccount addNewBalance(checkingAccount acc) {
        accounts.remove(acc);
        accounts.add(acc);
        return acc;
    }

}
