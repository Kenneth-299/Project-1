package com.revature.repository;

import com.revature.entity.User;
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
}
