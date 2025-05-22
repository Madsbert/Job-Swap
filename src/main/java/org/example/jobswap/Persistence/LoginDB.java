package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.LoginDBInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDB implements LoginDBInterface {

    /**
     * read in database the password and profileID
     * @param ProfileId
     * @param password the password the employee have chosen
     * @return a boolean returns true if password and employeeID matches
     */
    public boolean checkCredentials(int ProfileId, String password)
    {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT 1 FROM dbo.tbl_Login WHERE ProfileID = ? AND LoginPassword = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)){
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
}
