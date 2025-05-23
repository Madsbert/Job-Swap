package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.*;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
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
            preparedStatement.setInt(1, message.getSenderID());
            preparedStatement.setInt(2, message.getReceiverID());
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(message.getTime()));
            preparedStatement.setString(4, message.getText());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public List<Profile> getAllPossibleChatsBasedOnState(int LoggedInProfileID, MatchState stateOfMatch) {
        MatchDBInterface matchDB = new MatchDB();
        List<Match> matches = matchDB.getProfileMatches(LoggedInProfileID);
        List<Profile> possibleChats = new ArrayList<>();
        for (Match match : matches) {
            if (match.getMatchState() == stateOfMatch) {
                possibleChats.add(match.getOtherProfile());
            }
        }
            return possibleChats;

    }

    public List<Message> getMessages(int userId, int otherId, int matchId)
    {
        return null;
    }
}
