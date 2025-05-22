package org.example.jobswap.Persistence;

import org.example.jobswap.Foundation.DBConnection;
import org.example.jobswap.Model.*;
import org.example.jobswap.Persistence.Interfaces.MatchDBInterface;
import org.example.jobswap.Persistence.Interfaces.ProfileDBInterface;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * class to do CRUD in Matches
 */
public class MatchDB implements MatchDBInterface {
    public List<Match> getMatches(int profileID, MatchState state) {
        return null;
    }

    /**
     * creates a new match and in SQL it makes sure the match doesn't exist
     * and changes state if Person 1 has sent an application and then person 2 also sends an application
     * the state will then be both interested
     * @param match two {@link Profile}s are connected
     */
    public void createMatch(Match match)
    {
        String sp = "{call create_new_match(?,?,?,?)}";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)){
            cstmt.setInt(1, match.getOwnerProfile().getProfileID());
            cstmt.setInt(2, match.getOtherProfile().getProfileID());
            cstmt.setInt(3, match.getMatchStateInt());
            cstmt.setObject(4, match.getTimeOfMatch());
            cstmt.executeUpdate();
            System.out.println("Effected rows" + cstmt.getUpdateCount());

        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Something went wrong in createMatch");
            throw new RuntimeException(e);
        }
    }

    /**
     * The Stored procedure makes sure that one profile cant puch the button twice so its a match.
     * and it makes the state go from 3- interested to
     * @param match
     * @param LoggedInProfile
     */
    public void updateMatchStateFromBothInterestedToMatch(Match match,Profile LoggedInProfile) {
        String sp = "{call update_matchstate_of_both_interested_to_complete_match(?,?,?) }";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)) {
            cstmt.setInt(1, match.getOwnerProfile().getProfileID());
            cstmt.setInt(2, match.getOtherProfile().getProfileID());
            cstmt.setInt(3, LoggedInProfile.getProfileID());
            cstmt.executeUpdate();
            System.out.println("Effected rows" + cstmt.getUpdateCount());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Something went wrong in updateMatchStateFromBothInterestedToMatch");
            throw new RuntimeException(e);
        }
    }

    /**
     * a class which seeks for all the possible matches for the {@link Profile} with their current jobcategory and
     * the department they wish to work in
     * @param profileID an id for a {@link Profile}
     * @param wantedDepartment the department the {@link Profile} is seeking
     * @return
     */
    public static List<Profile> seekAllPossibleProfileMatches(int profileID, String wantedDepartment){
        String sp = "{call seek_all_possible_profile_matches(?,?) }";
        Connection conn = DBConnection.getConnection();
        try (CallableStatement cstmt = conn.prepareCall(sp)){
            cstmt.setInt(1, profileID);
            cstmt.setString(2, wantedDepartment);
            ResultSet rs = cstmt.executeQuery();
            List<Profile> profiles = new ArrayList<>();
            while(rs.next()){
                AccessLevel accessLevel = AccessLevel.values()[rs.getInt("AccessLevelID")];
                int matchProfileId = rs.getInt("ProfileID");
                String name = rs.getString("FullName");
                String username = rs.getString("Username");
                String department = rs.getString("DepartmentName");
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

    public List<Match> getProfileMatches(int profileID)
    {
        String sql = "SELECT * FROM tbl_Match where Profile1ID=? OR Profile2ID=?";
        Connection conn = DBConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, profileID);
            ps.setInt(2, profileID);
            ResultSet rs = ps.executeQuery();
            List<Match> matches = new ArrayList<>();
            ProfileDBInterface profileDB = new ProfileDB();
            while (rs.next()) {
                MatchState state = MatchState.values()[rs.getInt("MatchStateID")];
                Profile profile1 = profileDB.getProfileFromID(rs.getInt("Profile1ID"));
                Profile profile2 = profileDB.getProfileFromID(rs.getInt("Profile2ID"));
                LocalDateTime timeOfMatch = rs.getTimestamp("TimeOfMatch").toLocalDateTime();

                matches.add(new Match(state,profile1,profile2,timeOfMatch));
            }
            return matches;
        }catch (Exception e){
            System.out.println(e.getMessage() + "couldn't get matches in GetProfileMatches");
            return null;
        }
    }

    public void confirmJobswap(int matchID)
    {
        // HR - Not implemented
        // Will not be implemented in this iteration
    }

    public int getMatchIDFromProfile(int profileID)
    {
        return -1;
    }
}
