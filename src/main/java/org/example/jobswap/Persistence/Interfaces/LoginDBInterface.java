package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Profile;

public interface LoginDBInterface {
    public boolean checkCredentials(int workerId, String password);
    public void addLoginToDataBase(int profileID, String password);
    public boolean checkIsLocked(int ProfileId);
}
