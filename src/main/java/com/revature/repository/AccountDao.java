package com.revature.repository;

import com.revature.entity.checkingAccount;

import java.util.List;

public interface AccountDao {

    checkingAccount createAccount(checkingAccount username);

    List<checkingAccount> getChckingAccount(String username);

    checkingAccount deleteCheckingAccount(checkingAccount username);

    checkingAccount addNewBalance(checkingAccount acc);

}
