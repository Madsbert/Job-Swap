package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.*;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MatchDB implements MatchDBInterface {
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
    public List<Profile> seekAllPossibleProfileMatches(int profileID, Department wantedDepartment){
        String sp = "{call seek_all_possible_profile_matches(?,?,?,?,?,?,?,?,?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)){
            ResultSet rs = cstmt.executeQuery();
            List<Profile> profiles = new ArrayList<>();
            while(rs.next()){
                AccessLevel accessLevel = AccessLevel.values()[rs.getInt("AccessLevelID")];
                int matchProfileId = rs.getInt("ProfileID");
                String name = rs.getString("FullName");
                String username = rs.getString("Username");
                String department = rs.getString("Department");
                String jobTitle = rs.getString("JobTitle");
                String jobDescription = rs.getString("JobDescription");
                String jobCategory = rs.getString("JobCategory");
                boolean activelySeeking = rs.getBoolean("ActivelySeeking");
                profiles.add(new Profile(accessLevel,matchProfileId, name, username, department,
                        jobTitle, jobDescription, jobCategory, activelySeeking));
            }
            return profiles;

        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("something went wrong in seekAllPossibleProfileMatches");
            throw new RuntimeException(e);
        }

    }

    public Match getProfileAcceptedMatch()
    {
        return null;
    }

    public void confirmJobswap(int matchID)
    {
        // Not implemented
    }

    public int getMatchIDFromProfile(int profileID)
    {
        return -1;
    }
}
