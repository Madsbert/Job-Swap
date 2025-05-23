package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Message;
import org.example.jobswap.Model.Profile;

import java.util.HashMap;
import java.util.List;

public interface MessageDBInterface {
    void createMessage(Message message);
    List<Message> getMessages(int userId, int otherId, int matchId);
    HashMap<Integer,Integer> allChatsOfProfile(int LoggedInProfileID);

}
