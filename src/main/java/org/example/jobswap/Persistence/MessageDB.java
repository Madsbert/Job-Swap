package org.example.jobswap.Persistence;

import org.example.jobswap.Model.Message;
import org.example.jobswap.Persistence.Interfaces.MessageDBInterface;

import java.util.List;

public class MessageDB implements MessageDBInterface {
    public void createMessage(Message message) {

    }

    public List<Message> getMessages(int userId, int otherId, int matchId)
    {
        return null;
    }
}
