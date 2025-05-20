package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ProfileDB implements ProfileDBInterface {
    public Profile getProfile()
    {
        // Stored procedure
        return null;
    }

    public List<Profile> getAllProfiles()
    {
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

    public void createProfile(Profile profile)
    {

    }

    public void deleteProfile(int workerId)
    {

    }

    public void grantHRRights(int workerId)
    {

    }
}
