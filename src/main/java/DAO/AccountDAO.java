package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account createAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sqlStatment = "INSERT INTO Account (username, password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatment, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                int accountId = (int) result.getLong(1);
                Account accountCreation = new Account(accountId, account.getUsername(), account.getPassword());
                return accountCreation;
            }
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
            
        }
        return null;
    }

    public Account verifyAccount(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sqlStatement = "SELECT username, password FROM Account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                int accountId = (int) result.getLong(1);
                Account account = new Account(accountId, result.getString(username), result.getString(password));
                return account;
            }


        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
    


