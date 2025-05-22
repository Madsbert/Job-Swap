package org.example.jobswap.Persistence;

import javafx.scene.effect.Effect;
import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to do CRUD in Profiles
 */
public class ProfileDB implements ProfileDBInterface {
    public Profile getProfileFromID(int id)
    {
        // Stored procedure
        return null;
    }

    public List<Profile> getAllProfiles()
    {
      //Will not be implemented in this iteration
        return null;
    }

    public void unlockProfile(int profileID)
    {
        String query = "UPDATE tbl_Profile SET isLocked=false WHERE ProfileID = ?";
        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, profileID);

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * method to create a new profile in the database
     * @param profile an employee in the system
     */
    public static void createNewProfile(Profile profile)
    {
        String sp = "{call create_new_profile(?,?,?,?,?,?,?,?,?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)){
            cstmt.setString(1, profile.getDepartment());
            cstmt.setString(2, profile.getJobCategory());
            cstmt.setInt(3,profile.getAccessLevel());
            cstmt.setString(4, profile.getName());
            cstmt.setString(5, profile.getUsername());
            cstmt.setString(6, profile.getJobTitle());
            cstmt.setBoolean(7, profile.isActivelySeeking());
            cstmt.setString(8, profile.getJobDescription());
            cstmt.setBoolean(9, profile.isLocked());
            cstmt.executeUpdate();
            System.out.println("Effected rows" + cstmt.getUpdateCount());

        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("something went wrong in create_new_profile");
            throw new RuntimeException(e);
        }

    }

    public static void deleteProfile(int profileID)
    {
        String query = "DELETE from tbl_Profile WHERE ProfileID = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeQuery();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println(e.getStackTrace());
        }
    }

    public void grantHRRights(int workerId)
    {
//will not be implemented in this iteration
    }
}
