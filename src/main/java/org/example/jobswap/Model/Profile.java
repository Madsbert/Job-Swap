package org.example.jobswap.Model;

import java.util.Objects;

/**
 * a class which defines a Profile
 */
public class Profile {
    private AccessLevel level;
    private int profileID;
    private String name;
    private String username;
    private String department;
    private String jobTitle;
    private String jobDescription;
    private String jobCategory;
    private boolean activelySeeking;
    private boolean isLocked;

    public Profile(Profile profile) {
        this.level = profile.getLevel();
        this.profileID = profile.getProfileID();
        this.name = profile.getName();
        this.username = profile.getUsername();
        this.department = profile.getDepartment();
        this.jobTitle = profile.getJobTitle();
        this.jobDescription = profile.getJobDescription();
        this.jobCategory = profile.getJobCategory();
        this.activelySeeking = profile.isActivelySeeking();
        this.isLocked = profile.isLocked();
    }

    public Profile(AccessLevel level, int profileID, String name,String Username, String department, String jobTitle, String jobDescription, String jobCategory, boolean activelySeeking) {
        this.level = AccessLevel.EMPLOYEE;
        this.profileID = profileID;
        this.name = name;
        this.username = Username;
        this.department = department;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobCategory = jobCategory;
        this.activelySeeking = activelySeeking;
        this.isLocked = false;
    }
    public Profile(int profileID, String name,String Username, String department, String jobTitle, String jobDescription, String jobCategory, boolean activelySeeking) {
        this.level = AccessLevel.EMPLOYEE;
        this.profileID = profileID;
        this.name = name;
        this.username = Username;
        this.department = department;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobCategory = jobCategory;
        this.activelySeeking = activelySeeking;
        this.isLocked = false;
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

    public int getAccessLevel() {
        return level.ordinal();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setDescription(String description) {
        this.jobDescription = description;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileID=" + profileID +
                ", name='" + name+ '\'' +
                ", username='" + username + '\'' +
                ", department='" + department + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobCategory='" +  + '\'' +
                ", activelySeeking=" + activelySeeking +
                ", accessLevel=" + level +
                ", jobDescription='" + jobDescription + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (this.level != ((Profile) o).level) return false;
        if (this.profileID != ((Profile) o).profileID) return false;
        if (!Objects.equals(this.name, ((Profile) o).name)) return false;
        if (!Objects.equals(this.username, ((Profile) o).username)) return false;
        if (!Objects.equals(this.department, ((Profile) o).department)) return false;
        if (!Objects.equals(this.jobTitle, ((Profile) o).jobTitle)) return false;
        if (!Objects.equals(this.jobDescription, ((Profile) o).jobDescription)) return false;
        if (!Objects.equals(this.jobCategory, ((Profile) o).jobCategory)) return false;
        if (this.activelySeeking != ((Profile) o).activelySeeking) return false;

        return true;
    }
}
