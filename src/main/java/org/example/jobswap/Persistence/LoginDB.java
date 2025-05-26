package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDB implements LoginDBInterface {

    /**
     * Read in database the password and profileID
     * @param ProfileId
     * @param password the password the employee have chosen
     * @return a boolean returns true if password and employeeID matches
     */
    public boolean checkCredentials(int ProfileId, String password)
    {
        String query = "SELECT 1 FROM dbo.tbl_Login WHERE ProfileID = ? AND LoginPassword = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, ProfileId);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Something went wrong in Check Credentials");
        }
        return false;
    }

    /**
     * checks if a profile with isLocked or not.
     * @param ProfileId
     * @return
     */
    public boolean checkIsLocked(int ProfileId){
        String query = "SELECT IsLocked FROM dbo.tbl_Profile WHERE ProfileID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)){
            ps.setInt(1, ProfileId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println("Something went wrong in checkIsLocked");
        }
        return false;
    }

    /**
     * Makes a login- record in tbl login.
     * @param profileID
     * @param password
     */
    public void addLoginToDataBase(int profileID, String password){
        String query = "INSERT INTO tbl_Login VALUES (?,?)";
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, profileID);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getStackTrace());
        }
    }
}
