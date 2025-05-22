package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Match;
import org.example.jobswap.Model.Profile;

import java.util.List;

public interface ProfileDBInterface {
    Profile getProfileFromID(int id);
    List<Profile> getAllProfiles();
    void unlockProfile(int workerId);

    public boolean createNewProfile(Profile profile);

    public void deleteProfile(int workerId);
    static void updateProfile(Profile profile) {
    }

    void grantHRRights(int workerId);
}
