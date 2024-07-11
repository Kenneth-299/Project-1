package com.revature.entity;

import java.io.Serializable;
import java.util.Objects;

public class checkingAccount implements Serializable {

    private String username;
    private Integer balance;

    public checkingAccount(){}

    public checkingAccount(String username, Integer balance){
        this.username = username;
        this.balance = balance;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        checkingAccount account  = (checkingAccount) o;
        return Objects.equals(getUsername(), account.getUsername()) && Objects.equals(getBalance(), account.getBalance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getBalance()) ;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + balance + '\'' +
                '}';
    }
}
