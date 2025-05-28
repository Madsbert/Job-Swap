package org.example.jobswap.Model;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * class to define a {@link Report}
 * it has a date, {@link Profile}ID of reporter and {@link Profile}ID of Sender
 */
public class Report {
    private LocalDateTime date;
    private int profileIDOfReporter;
    private int profileIDOfReported;


    public Report(int profileIDOfReporter, int profileIDOfReported) {
        this.profileIDOfReporter = profileIDOfReporter;
        this.profileIDOfReported = profileIDOfReported;
    }

    public int getProfileIDOfReported() {
        return profileIDOfReported;
    }

    public void setProfileIDOfReported(int profileIDOfReported) {
        this.profileIDOfReported = profileIDOfReported;
    }

    public int getProfileIDOfReporter() {
        return profileIDOfReporter;
    }

    public void setProfileIDOfReporter(int profileIDOfReporter) {
        this.profileIDOfReporter = profileIDOfReporter;
    }

}

