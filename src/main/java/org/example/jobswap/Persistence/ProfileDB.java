package org.example.jobswap.Persistence;

import javafx.scene.effect.Effect;
import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.AccessLevel;
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

    /**
     * gets the profile from ID
     * @param profileID
     * @return a Profile
     */
    public Profile getProfileFromID(int profileID) {
        String sp = "{call getProfileFromID(?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)) {
            cstmt.setInt(1, profileID);
            ResultSet rs = cstmt.executeQuery();
            Profile profile = null;
            if (rs.next()) {
                AccessLevel accessLevel = AccessLevel.values()[rs.getInt("AccessLevelID")];
                int matchProfileId = rs.getInt("ProfileID");
                String name = rs.getString("FullName");
                String username = rs.getString("Username");
                String department = rs.getString("DepartmentName");
                String jobTitle = rs.getString("JobTitle");
                String jobDescription = rs.getString("JobDescription");
                String jobCategory = rs.getString("JobCategory");
                boolean activelySeeking = rs.getBoolean("ActivelySeeking");
                profile = new Profile(accessLevel,matchProfileId, name, username, department,
                        jobTitle, jobDescription, jobCategory, activelySeeking);
            }
            return profile;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("something went wrong in GetProfileFromID");
            throw new RuntimeException(e);
        }
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
        String sp = "{call create_new_profile(?,?,?,?,?,?,?,?,?,?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)){
            cstmt.setInt(1, profile.getProfileID());
            cstmt.setString(2, profile.getDepartment());
            cstmt.setString(3, profile.getJobCategory());
            cstmt.setInt(4,profile.getAccessLevel());
            cstmt.setString(5, profile.getName());
            cstmt.setString(6, profile.getUsername());
            cstmt.setString(7, profile.getJobTitle());
            cstmt.setBoolean(8, profile.isActivelySeeking());
            cstmt.setString(9, profile.getJobDescription());
            cstmt.setBoolean(10, profile.isLocked());
            cstmt.executeUpdate();
            System.out.println("Effected rows" + cstmt.getUpdateCount());

        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("something went wrong in create_new_profile");
            throw new RuntimeException(e);
        }

    }

    public static void updateProfile(Profile profile)
    {
        String sp = "{call update_profile(?,?,?,?,?,?,?,?,?,?)}";

        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)){
            cstmt.setInt(1, profile.getProfileID());
            cstmt.setString(2, profile.getDepartment());
            cstmt.setString(3, profile.getJobCategory());
            cstmt.setInt(4,profile.getAccessLevel());
            cstmt.setString(5, profile.getName());
            cstmt.setString(6, profile.getUsername());
            cstmt.setString(7, profile.getJobTitle());
            cstmt.setBoolean(8, profile.isActivelySeeking());
            cstmt.setString(9, profile.getJobDescription());
            cstmt.setBoolean(10, profile.isLocked());
            cstmt.executeUpdate();
            System.out.println("Effected rows" + cstmt.getUpdateCount());

        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("something went wrong in update_profile");
            throw new RuntimeException(e);
        }
    }

    public static void deleteProfile(int profileID)
    {
        String query = "DELETE from tbl_Profile WHERE ProfileID = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, profileID);
            preparedStatement.executeUpdate();
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
