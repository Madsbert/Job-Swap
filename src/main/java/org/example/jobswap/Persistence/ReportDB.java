package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Message;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Model.Report;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;
import org.example.jobswap.Persistence.Interfaces.ReportDBInterface;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class to handle database querys and stored procedures of {@link Report}
 */
public class ReportDB implements ReportDBInterface {

    /**
     * creates a {@link Report} with the two IDs
     * @param profileIDOfReporter the {@link Profile}ID of the reporter
     * @param profileIDOfReported the {@link Profile}ID of the reported
     */
    public void createReport(int profileIDOfReporter, int profileIDOfReported) {
        String sql = "INSERT INTO tbl_Report (ProfileIDOfReporter, ProfileIDOfReported ) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)){
            cstmt.setInt(1, profileIDOfReporter);
            cstmt.setInt(2, profileIDOfReported);
            cstmt.execute();
            System.out.println("Effected rows" + cstmt.getUpdateCount());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage()+ "Something went wrong in createReport");
            throw new RuntimeException(e);
        }
    }

    /**
     * checks if there is a report bet the users
     * @param profileIDOfReporter the {@link Profile}ID of the reporter
     * @param profileIDOfReported the {@link Profile}ID of the reported
     * @return a boolean if a report exists
     */
    public boolean checkIfReportExistsBetweenUsers(int profileIDOfReporter, int profileIDOfReported) {
        String preparedStatement = "SELECT TOP 1 * FROM tbl_Report WHERE ProfileIDOfReporter = ? AND ProfileIDOfReported = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(preparedStatement);) {

            ps.setInt(1, profileIDOfReporter);
            ps.setInt(2, profileIDOfReported);
            try (ResultSet rs = ps.executeQuery()) {
                boolean exists = rs.next();
                return exists;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage() + "couldn't get message in newestMessageByLoggedInProfile");
        }
        return false;
    }

    public List<Report> readReports()
    {
        return null;
    }
}
