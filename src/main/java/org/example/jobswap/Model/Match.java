package org.example.jobswap.Model;

public class Match {
    private MatchState state;
    public Profile ownerProfile;
    public Profile otherProfile;

    public void UpdateState(MatchState newState)
    {
        this.state = newState;
    }

    public void setMatchState()
    {

    }

    public void getMatchState()
    {

    }
}
