package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    Connection connection = ConnectionUtil.getConnection();
    
    public Account register(Account acc) {
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ps.executeUpdate();
            ResultSet pKeyResultSet = ps.getGeneratedKeys();
            if (pKeyResultSet.next()) {
                return new Account((int) pKeyResultSet.getLong(1), acc.getUsername(), acc.getPassword());
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account login(Account acc) {
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account((int) rs.getLong("account_id"), rs.getString("username"), rs.getString("password"));
            } 
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    

}
