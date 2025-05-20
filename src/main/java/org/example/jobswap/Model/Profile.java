package org.example.jobswap.Model;

public class Profile {
    private AccessLevel level;
    private int profileID;
    private String name;
    private String department;
    private String jobTitle;
    private String description;
    private String JobCategory;
    private boolean activelySeeking;
    private boolean isLocked;

    public Profile(AccessLevel level, int profileID, String name, String department, String jobTitle, String description, String JobCategory, boolean activelySeeking, boolean isLocked) {
        this.level = level;
        this.profileID = profileID;
        this.name = name;
        this.department = department;
        this.jobTitle = jobTitle;
        this.description = description;
        this.JobCategory = JobCategory;
        this.activelySeeking = activelySeeking;
        this.isLocked = isLocked;
    }

    public AccessLevel getLevel() {
        return level;
    }

    public int getProfileID() {
        return profileID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobCategory() {
        return JobCategory;
    }

    public void setJobCategory(String jobCategory) {
        JobCategory = jobCategory;
    }

    public boolean isActivelySeeking() {
        return activelySeeking;
    }

    public void setActivelySeeking(boolean activelySeeking) {
        this.activelySeeking = activelySeeking;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void updateInformation()
    {

    }
}
