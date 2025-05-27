package org.example.jobswap.Persistence;

import org.example.jobswap.Model.Department;
import org.example.jobswap.Model.Report;
import org.example.jobswap.Persistence.Interfaces.ReportDBInterface;

import java.util.List;

/**
 * Class to handle database querys and stored procedures of {@link Report}
 */
public class ReportDB implements ReportDBInterface {
    public void createReport(Report report) {

    }

    public List<Report> readReports()
    {
        return null;
    }
}
