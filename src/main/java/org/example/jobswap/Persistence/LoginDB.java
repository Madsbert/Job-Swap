package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoginDB implements LoginDBInterface {

    public Profile getCredentials(int workerId)
    {
        return null;
    }


    public void addLoginToDataBase(int profileID, String password){
        String query = "INSERT INTO tbl_Login VALUES (?,?)";
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
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
