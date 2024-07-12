package com.revature.repository;

import com.revature.entity.checkingAccount;
import com.revature.exception.UserSQLException;
import com.revature.utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqliteAccountDao implements AccountDao{

    @Override
    public checkingAccount createAccount(checkingAccount account) {
        String sql = "insert into account values (?, ?)";
        try(Connection connection = DatabaseConnector.createConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setInt(2, account.getBalance());

            int result = preparedStatement.executeUpdate();
            if(result == 1){
                return account;
            }

        }
        catch (SQLException exception){
            throw new UserSQLException(exception.getMessage());
        }
        return null;
    }
}
