package org.example.jobswap.Model;

public class Department {
    private int departmentID;
    private String departmentName;
    private Profile hrProfile;
    private String city;
    public Department(int departmentID, String departmentName, Profile hrProfile, String city) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.hrProfile = hrProfile;
        this.city = city;
    }
}
