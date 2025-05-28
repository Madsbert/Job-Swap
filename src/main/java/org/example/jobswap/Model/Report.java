package org.example.jobswap.Model;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * class to define a {@link Report}
 * it has a date, type and a {@link Message}
 */
public class Report {
    private LocalDateTime date;
    private int profileIDOfReporter;
    private int profileIDOfReported;

    public Report(int profileIDOfReporter, int profileIDOfReported) {
        this.profileIDOfReporter = profileIDOfReporter;
        this.profileIDOfReported = profileIDOfReported;

    }

}

