package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.Profile;

import java.util.List;

public interface ProfileDBInterface {
    Profile getProfileFromID(int id);
    List<Profile> getAllProfiles();
    void unlockProfile(int workerId);

    static void createNewProfile(Profile profile) {

    }

    static void updateProfile(Profile profile) {

    }

    static void deleteProfile(int workerId) {

    }

    void grantHRRights(int workerId);
}
