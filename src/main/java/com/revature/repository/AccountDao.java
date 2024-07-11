package com.revature.repository;

import com.revature.entity.checkingAccount;

public interface AccountDao {

    checkingAccount createAccount(String username);
}
