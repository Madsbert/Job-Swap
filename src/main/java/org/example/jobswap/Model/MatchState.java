package org.example.jobswap.Model;

/**
 * enum of the states a {@link Match} can have
 */
public enum MatchState {
    BUFFER, // DO NOT USE.
    REQUESTED,
    APPLICATION,
    BOTH_INTERESTED,
    MATCH,
    ONE_PROFILE_IS_READY_TO_MATCH,
    SIX,
    ACCEPTED_MATCH,
}
