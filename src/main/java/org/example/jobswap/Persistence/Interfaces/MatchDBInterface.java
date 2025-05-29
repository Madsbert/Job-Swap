package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.MatchState;
import org.example.jobswap.Model.Profile;

import java.util.List;

public interface MatchDBInterface {
    List<Match> getMatches(int profileID, MatchState state);

    void createMatch(Match match);

    void updateMatchStateFromBothInterestedToMatch(Match match, Profile LoggedInProfile);

    List<Match> getProfileMatches(int profileID);

    void confirmJobswap(int matchID);

    Match getMatchFromProfileIDs(int ownerProfileID, int otherProfile);
    boolean updateMatch(Match match);
    boolean deleteMatch(int ownerProfileID, int otherProfile);
    List<Profile> seekAllPossibleProfileMatches(int profileID, String wantedDepartment);
}
