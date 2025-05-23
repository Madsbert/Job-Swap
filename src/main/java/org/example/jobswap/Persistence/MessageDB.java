package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.*;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
import org.example.jobswap.Persistence.Interfaces.MessageDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageDB implements MessageDBInterface {

    public void createMessage(Message message) {
        String ps = "INSERT INTO tbl_Message (ProfileIDOfSender, ProfileIDOfReceiver, TimeOfMessage, MessageText) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(ps)){
            cstmt.setInt(1, message.getSenderID());
            cstmt.setInt(2, message.getReceiverID());
            cstmt.setTimestamp(3, java.sql.Timestamp.valueOf(message.getTime()));
            cstmt.setString(4, message.getText());
            cstmt.execute();
            System.out.println("Effected rows" + cstmt.getUpdateCount());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public HashMap<Integer,Integer> allChatsOfProfile(int LoggedInProfileID) {
        String preparedStatement = "SELECT * FROM tbl_Message WHERE ProfileIDOfSender = ? OR ProfileIDOfReceiver = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(preparedStatement);) {
            ps.setInt(1, LoggedInProfileID);
            ps.setInt(2, LoggedInProfileID);
            ResultSet rs = ps.executeQuery();
            HashMap<Integer,Integer> profileIDs = new HashMap();
            while (rs.next()) {
               profileIDs.put(rs.getInt("ProfileIDOfSender"), rs.getInt("ProfileIDOfReceiver"));
            }
            return profileIDs;

        }catch (Exception e){
            System.out.println(e.getMessage() + "couldn't get matches in allChatsOfProfile");
            throw new RuntimeException(e);
        }
    }

    public List<Message> getMessages(int userId, int otherId, int matchId)
    {
        return null;
    }
}
