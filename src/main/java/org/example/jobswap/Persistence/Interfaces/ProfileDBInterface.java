package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Profile;

import java.util.List;

public interface ProfileDBInterface {
    Profile getProfile();
    List<Profile> getAllProfiles();
    void unlockProfile(int workerId);
    void createProfile(Profile profile);
    void deleteProfile(int workerId);
    void grantHRRights(int workerId);
}
