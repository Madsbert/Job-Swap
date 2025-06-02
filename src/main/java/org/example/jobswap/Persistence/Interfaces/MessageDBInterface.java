package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Message;
import org.example.jobswap.Model.Profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MessageDBInterface {
    void createMessage(Message message);
    List<Message> getMessages(int userId, int otherId);
    Map<Integer,Integer> allChatsOfProfile(int LoggedInProfileID);
    Message newestMessageByLoggedInProfile(int LoggedInProfileID);
}
