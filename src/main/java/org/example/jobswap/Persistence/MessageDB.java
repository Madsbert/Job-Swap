package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.Message;
import org.example.jobswap.Persistence.Interfaces.MessageDBInterface;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public List<Message> getMessages(int userId, int otherId, int matchId)
    {
        return null;
    }
}
