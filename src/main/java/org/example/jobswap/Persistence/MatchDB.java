package org.example.jobswap.Persistence;

import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.MatchState;

import java.util.List;

public class MatchDB {
    public List<Match> getMatches(int profileID, MatchState state)
    {
        return null;
    }

    public void createMatch(Match match)
    {

    }

    public void updateMatchState(int matchID, MatchState state)
    {

    }

    public List<Match> getAcceptedMatches()
    {
        return null;
    }

    public void confirmJobswap(int matchID)
    {

    }

    public int getMatchIDFromProfile(int profileID)
    {
        return -1;
    }
}
