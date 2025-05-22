package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.AccessLevel;
import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Message;
import org.example.jobswap.Model.Profile;
import org.example.jobswap.Persistence.Interfaces.MessageDBInterface;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MessageDB implements MessageDBInterface {

    public void createMessage(Message message) {
        String sql = "INSERT INTO tbl_Message (sender, receiver, date, MessageText) VALUES (?, ?, ?, ?)";

        Connection conn = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, message.getSender().getProfileID());
            preparedStatement.setInt(2, message.getReceiver().getProfileID());
            preparedStatement.setDate(3, Date.valueOf(LocalDateTime.now().toLocalDate()));
            preparedStatement.setString(4, message.getText());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public List<Profile> getAllPossibleChatsBasedOnState(int LoggedInProfileID, MatchState stateOfMatch) {

        //Vi vil have en Liste af profiles som vi kan lave en Chat med, vi skal kigge på de matches hvor logged
        //er der, og hvor matchstatet et fx. Both_Interested.
        //Vi vil altså gerne retunere profiler, og ikke kun profileID som man så kunne kalde .getProfileFromID
        //Fordi det koster performance.

        //Derfor skal det laves i en stored procedure, og deeeeeet gider jeg ikke rigtig til nu. hehe.

        String sp = "{call get_all_possible_chats_based_on_matchstate(?,?) }";

        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)) {
            cstmt.setInt(1, LoggedInProfileID);
            cstmt.setString(2, stateOfMatch.toString());
            ResultSet rs = cstmt.executeQuery();
            List<Profile> profiles = new ArrayList<>();
            while (rs.next()) {
                AccessLevel accessLevel = AccessLevel.values()[rs.getInt("AccessLevelID")];
                int matchProfileId = rs.getInt("ProfileID");
                String name = rs.getString("FullName");
                String username = rs.getString("Username");
                String department = rs.getString("DepartmentName");
                String jobTitle = rs.getString("JobTitle");
                String jobDescription = rs.getString("JobDescription");
                String jobCategory = rs.getString("JobCategory");
                boolean activelySeeking = rs.getBoolean("ActivelySeeking");
                profiles.add(new Profile(accessLevel, matchProfileId, name, username, department,
                        jobTitle, jobDescription, jobCategory, activelySeeking));
            }
            return profiles;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("something went wrong in getAllPossibleChatsBasedOnState");
            throw new RuntimeException(e);
        }
    }

    public List<Message> getMessages(int userId, int otherId, int matchId)
    {
        return null;
    }
}
