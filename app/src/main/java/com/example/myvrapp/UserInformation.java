package com.example.myvrapp;

import java.io.Serializable;

public class UserInformation implements Serializable {

    private String visitTemple;
    private String numberOfHoursSlept;
    private String appetite;
    private String name;
    private String email;

    private String communitySupport;
    private String healthAndEnergyLevel;
    private String psychologicalWellBeing;
    private String timeBalance;
    private String userId;
    private String religion;
    private String location;

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVrInvolvement() {
        return vrInvolvement;
    }

    public void setVrInvolvement(String vrInvolvement) {
        this.vrInvolvement = vrInvolvement;
    }

    private String vrInvolvement;

    public String getCommunitySupport() {
        return communitySupport;
    }

    public void setCommunitySupport(String communitySupport) {
        this.communitySupport = communitySupport;
    }

    public String getHealthAndEnergyLevel() {
        return healthAndEnergyLevel;
    }

    public void setHealthAndEnergyLevel(String healthAndEnergyLevel) {
        this.healthAndEnergyLevel = healthAndEnergyLevel;
    }

    public String getPsychologicalWellBeing() {
        return psychologicalWellBeing;
    }

    public void setPsychologicalWellBeing(String psychologicalWellBeing) {
        this.psychologicalWellBeing = psychologicalWellBeing;
    }

    public String getTimeBalance() {
        return timeBalance;
    }

    public void setTimeBalance(String timeBalance) {
        this.timeBalance = timeBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserInformation() {
    }

    public String getVisitTemple() {
        return visitTemple;
    }

    public void setVisitTemple(String visitTemple) {
        this.visitTemple = visitTemple;
    }

    public String getNumberOfHoursSlept() {
        return numberOfHoursSlept;
    }

    public void setNumberOfHoursSlept(String numberOfHoursSlept) {
        this.numberOfHoursSlept = numberOfHoursSlept;
    }

    public String getAppetite() {
        return appetite;
    }

    public void setAppetite(String appetite) {
        this.appetite = appetite;
    }
}
