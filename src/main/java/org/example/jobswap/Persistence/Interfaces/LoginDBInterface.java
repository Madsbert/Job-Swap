package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Profile;

public interface LoginDBInterface {
    public Profile getCredentials(int workerId);

    public void addLoginToDataBase(int profileID, String password);
}
