package com.accessiblelife.admin;

import com.accessiblelife.place.Place;
import com.accessiblelife.ratingreview.RatingReview;
import com.accessiblelife.user.User;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User
{
    private String adminID;
    private String permissions;
    private List<Place> createdPlaces;
    private List<String> actionLog;
    private List<Report> assignedReports;
    private boolean isActive;

    public Admin()
    {
        this.createdPlaces = new ArrayList<>();
        this.assignedReports = new ArrayList<>();
        this.actionLog = new ArrayList<>();
        this.isActive = true;
    }

    public Admin(String adminID, String name, String email, String permissions)
    {
        super(name, email);
        this.adminID = adminID;
        this.permissions = permissions;
        this.createdPlaces = new ArrayList<>();
        this.assignedReports = new ArrayList<>();
        this.actionLog = new ArrayList<>();
        this.isActive = true;
    }

    public void approvePlaces(Place place)
    {
        System.out.println("Approving place: " + place.getShortDescription());
        this.createdPlaces.add(place);
        logAction("Approved place: " + place.getShortDescription());
    }

    public void deleteUser(User user)
    {
        System.out.println("Deleting User: " + user.getName());
        logAction("Deleted User: " + user.getName());
    }

    public void viewReports()
    {
        System.out.println("Assigned reports: " + assignedReports);
        logAction("Viewed reports");
    }

    public void editAnyPlace(Place place, String newDescription)
    {
        System.out.println("Editing any place: " + place.getShortDescription());
        place.setShortDescription(newDescription);
        logAction("Edited place: " + place.getShortDescription());
    }

    public void removeReview(RatingReview review)
    {
        System.out.println("Removing review by: " + review.getUser());
        logAction("Removed review by: " + review.getUser());
    }

    public void logAction(String action)
    {
        this.actionLog.add(action);
    }

    public String getAdminID()
    {
        return adminID;
    }

    public void setAdminID(String adminID)
    {
        this.adminID = adminID;
    }

    public String getPermissions()
    {
        return permissions;
    }

    public void setPermissions(String permissions)
    {
        this.permissions = permissions;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        this.isActive = active;
    }

    public List<Place> getCreatedPlaces()
    {
        return createdPlaces;
    }

    public List<String> getActionLog()
    {
        return actionLog;
    }

    public List<Report> getAssignedReports()
    {
        return assignedReports;
    }
}