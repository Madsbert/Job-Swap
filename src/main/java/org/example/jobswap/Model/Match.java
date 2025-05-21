package org.example.jobswap.Model;

import java.time.LocalDateTime;

/**
 * a class which defines a Match
 */
public class Match {
    private MatchState state;
    private Profile ownerProfile;
    private Profile otherProfile;
    private LocalDateTime timeOfMatch;


    public Profile getOwnerProfile() {
        return ownerProfile;
    }

    public void setOwnerProfile(Profile ownerProfile) {
        this.ownerProfile = ownerProfile;
    }

    public Profile getOtherProfile() {
        return otherProfile;
    }

    public void setOtherProfile(Profile otherProfile) {
        this.otherProfile = otherProfile;
    }

    public LocalDateTime getTimeOfMatch() {
        return timeOfMatch;
    }

    public void setTimeOfMatch(LocalDateTime timeOfMatch) {
        this.timeOfMatch = timeOfMatch;
    }

    public void UpdateState(MatchState newState)
    {
        this.state = newState;
    }

    public int getMatchStateInt() {
        return state.ordinal();
    }

    public MatchState getMatchState() {return state;}
}
