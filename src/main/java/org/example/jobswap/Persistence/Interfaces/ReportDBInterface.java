package org.example.jobswap.Persistence.Interfaces;

import org.example.jobswap.Model.Report;

import java.util.List;

public interface ReportDBInterface {
    void createReport(int profileIDOfReporter, int profileIDOfReported);
    List<Report> readReports();
    boolean checkIfReportExistsBetweenUsers(int profileIDOfReporter, int profileIDOfReported);
}
