package com.revature.repository;

import com.revature.entity.User;
import com.revature.entity.checkingAccount;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteAccountDao implements AccountDao{

    @Override
    public checkingAccount createAccount(checkingAccount account) {
        String sql = "Insert into account values (?, ?)";
        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setDouble(2, account.getBalance());

            int result = preparedStatement.executeUpdate();
            if(result == 1){
                return account;
            }

            throw new UserSQLException("Checking account could not be created: please try again");

        }
        catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public List<checkingAccount> getChckingAccount(String username) {

        String sql = "Select * from account where username = ?";

        try(Connection connection = DatabaseConnector.createConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<checkingAccount> newChecking = new ArrayList<>();

            while(resultSet.next()){
                checkingAccount acc = new checkingAccount();

                acc.setUsername(resultSet.getString("username"));
                acc.setBalance(resultSet.getDouble("balance"));

                newChecking.add(acc);
            }
            return newChecking;

        } catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public checkingAccount deleteCheckingAccount(checkingAccount account) {
        String sql = "Delete from account where username = ?";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());

            int result = preparedStatement.executeUpdate();
            if(result == 1){
                return account;
            }

            throw new UserSQLException("Not able to delete checking account: please try again");

        }
        catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }

    @Override
    public checkingAccount addNewBalance(checkingAccount acc) {
        String sql = "Update account set balance = ? where username = ?";

        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setDouble(1, acc.getBalance());
            preparedStatement.setString(2, acc.getUsername());

            int result = preparedStatement.executeUpdate();
            if(result == 1){
                return acc;
            }

            throw new UserSQLException("Not able to withdraw/deposit money: please try again");

        }
        catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
    }
}
