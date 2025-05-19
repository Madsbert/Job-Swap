package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.MatchState;

import java.util.List;

public interface MatchDBInterface {
    List<Match> getMatches(int profileID, MatchState state);
    void createMatch(Match match);
    void updateMatchState(int matchID, MatchState state);
    List<Match> getAcceptedMatches();
    void confirmJobswap(int matchID);
    int getMatchIDFromProfile(int profileID);
}
