package org.example.jobswap.Model;

/**
 * class to define a {@link Department}
 * it has an ID, Name and city
 */
public class Department {
    private int departmentID;
    private String departmentName;
    private String city;
    public Department(int departmentID, String departmentName, String city) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.city = city;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getCity() {
        return city;
    }
}
