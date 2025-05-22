package org.example.jobswap.Model;

import java.util.Objects;

/**
 * a class which defines a Profile
 */
public class Profile {
    private AccessLevel level;
    private int profileID;
    private String name;
    private String Username;
    private String department;
    private String jobTitle;
    private String JobDescription;
    private String JobCategory;
    private boolean activelySeeking;
    private boolean isLocked;

    public Profile(Profile profile) {
        this.level = profile.getLevel();
        this.profileID = profile.getProfileID();
        this.name = profile.getName();
        this.Username = profile.getUsername();
        this.department = profile.getDepartment();
        this.jobTitle = profile.getJobTitle();
        this.JobDescription = profile.getJobDescription();
        this.JobCategory = profile.getJobCategory();
        this.activelySeeking = profile.isActivelySeeking();
        this.isLocked = profile.isLocked();
    }

    public Profile(AccessLevel level, int profileID, String name,String Username, String department, String jobTitle, String jobDescription, String jobCategory, boolean activelySeeking) {
        this.level = level;
        this.profileID = profileID;
        this.name = name;
        this.Username = Username;
        this.department = department;
        this.jobTitle = jobTitle;
        this.JobDescription = jobDescription;
        this.JobCategory = jobCategory;
        this.activelySeeking = activelySeeking;
        this.isLocked = false;
    }

    public Profile(AccessLevel level, String name, String Username, String department, String jobTitle, String jobDescription, String jobCategory, boolean activelySeeking) {
        this.name = name;
        this.Username = Username;
        this.level = level;
        this.department = department;
        this.jobTitle = jobTitle;
        this.JobCategory = jobCategory;
        this.activelySeeking = activelySeeking;
        this.JobDescription = jobDescription;
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
        return JobDescription;
    }

    public void setDescription(String description) {
        this.JobDescription = description;
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

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (this.level != ((Profile) o).level) return false;
        if (this.profileID != ((Profile) o).profileID) return false;
        if (!Objects.equals(this.name, ((Profile) o).name)) return false;
        if (!Objects.equals(this.Username, ((Profile) o).Username)) return false;
        if (!Objects.equals(this.department, ((Profile) o).department)) return false;
        if (!Objects.equals(this.jobTitle, ((Profile) o).jobTitle)) return false;
        if (!Objects.equals(this.JobDescription, ((Profile) o).JobDescription)) return false;
        if (!Objects.equals(this.JobCategory, ((Profile) o).JobCategory)) return false;
        if (this.activelySeeking != ((Profile) o).activelySeeking) return false;
        return true;
    }
}
