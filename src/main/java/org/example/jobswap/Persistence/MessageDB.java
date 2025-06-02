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
import java.util.Map;

/**
 * Class to handle database querys and stored procedures of {@link Message}
 */
public class MessageDB implements MessageDBInterface {

    /**
     * Creates a new record in tbl_Message
     * @param message a {@link Message}
     */
    public void createMessage(Message message) {
        String ps = "INSERT INTO tbl_Message (ProfileIDOfSender, ProfileIDOfReceiver, TimeOfMessage, MessageText) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); CallableStatement cstmt = conn.prepareCall(ps)){
            cstmt.setInt(1, message.getSenderID());
            cstmt.setInt(2, message.getReceiverID());
            cstmt.setTimestamp(3, Timestamp.valueOf(message.getTime()));
            cstmt.setString(4, message.getText());
            cstmt.execute();
            System.out.println("Effected rows" + cstmt.getUpdateCount());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Return a HashMap of ProfileIDs and links them up, sender and receiver. LoggedInProfileID might be sender or receiver.
     * It gets every profileID, LoggedInProfileID has ever messaged.
     * @param LoggedInProfileID the id of the logged in {@link Profile}
     * @return a Hashmap of the {@link Profile}IDs
     */
    public Map<Integer,Integer> allChatsOfProfile(int LoggedInProfileID) {
        String preparedStatement = "SELECT * FROM tbl_Message WHERE ProfileIDOfSender = ? OR ProfileIDOfReceiver = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(preparedStatement);) {
            ps.setInt(1, LoggedInProfileID);
            ps.setInt(2, LoggedInProfileID);
            ResultSet rs = ps.executeQuery();
            Map<Integer,Integer> profileIDs = new HashMap();
            while (rs.next()) {
                profileIDs.put(rs.getInt("ProfileIDOfSender"),
                       rs.getInt("ProfileIDOfReceiver"));
            }
            return profileIDs;

        }catch (Exception e){
            System.out.println(e.getMessage() + "couldn't get profiles in allChatsOfProfile");
            throw new RuntimeException(e);
        }
    }

    /**
     * returns the {@link Message} Object that has the newest time of creation.
     * @param LoggedInProfileID the id of the logged in {@link Profile}
     * @return a {@link Message}
     */
    public Message newestMessageByLoggedInProfile(int LoggedInProfileID) {
        String preparedStatement = "SELECT TOP 1 * FROM tbl_Message WHERE ProfileIDOfSender = ? OR ProfileIDOfReceiver = ? ORDER BY TimeOfMessage DESC";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(preparedStatement);) {

            ps.setInt(1, LoggedInProfileID);
            ps.setInt(2, LoggedInProfileID);
            ResultSet rs = ps.executeQuery();
            Message newestMessage = null;

            ProfileDBInterface profileDB = new ProfileDB();
            while (rs.next()) {
                Profile profileIDOfSender = profileDB.getProfileFromID(rs.getInt("ProfileIDOfSender"));
                Profile profileIDOfReceiver = profileDB.getProfileFromID(rs.getInt("ProfileIDOfReceiver"));
                String messageText = rs.getString("MessageText");
                LocalDateTime timeOfMatch = rs.getTimestamp("TimeOfMessage").toLocalDateTime();

                newestMessage = new Message(profileIDOfSender.getProfileID(),profileIDOfReceiver.getProfileID(),messageText,timeOfMatch);
            }
                 return newestMessage;
        }catch (Exception e){
            System.out.println(e.getMessage() + "couldn't get message in newestMessageByLoggedInProfile");
            throw new RuntimeException(e);
        }


    }

    /**
     * Returns a list of {@link Profile} Obejct for every chat between two profileIDs.
     * The Stored Procedure gets every record, not matter who is Sender and Receiver of the {@link Message}.
     * @param LoggedInProfileID the ID og the logged in {@link Profile}
     * @param ProfileIDReceiver the Id of the other {@link Profile}
     * @return a List of {@link Message}s
     */
    public List<Message> getMessages(int LoggedInProfileID, int ProfileIDReceiver)
    {
        String storeProcedure = "{call get_all_messages_between_2_profiles_sort_by_time(?,?) }";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(storeProcedure);) {
            List<Message> messages = new ArrayList<>();

            ps.setInt(1, LoggedInProfileID);
            ps.setInt(2, ProfileIDReceiver);
            ResultSet rs = ps.executeQuery();

            ProfileDBInterface profileDB = new ProfileDB();
            while (rs.next()) {
                Profile profileIDOfSender = profileDB.getProfileFromID(rs.getInt("ProfileIDOfSender"));
                Profile profileIDOfReceiver = profileDB.getProfileFromID(rs.getInt("ProfileIDOfReceiver"));
                String messageText = rs.getString("MessageText");
                LocalDateTime timeOfMatch = rs.getTimestamp("TimeOfMessage").toLocalDateTime();

                messages.add(new Message(profileIDOfSender.getProfileID(),profileIDOfReceiver.getProfileID(),messageText,timeOfMatch));
            }
            return messages;
        }catch (Exception e){
            System.out.println(e.getMessage() + "couldn't get messages in GetMessages");
            throw new RuntimeException(e);
        }
    }
}
