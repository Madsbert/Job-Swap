package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Profile;

public interface LoginDBInterface {
    boolean checkCredentials(int workerId, String password);
    void addLoginToDataBase(int profileID, String password);
    boolean checkIsLocked(int ProfileId);
}
