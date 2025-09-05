import java.util.ArrayList;
import java.util.List;
public class Admin extends User{
    private String adminID;
    private String name;
    private String email;
    private String permissions;
    private List<Place> createdPlaces;
    private String actionLog;
    private List<Report> assignedReports;
    private boolean isActive;
    public Admin() {
        this.createdPlaces = new ArrayList<>();
        this.assignedReports = new ArrayList<>();
        this.isActive = true;
    }
    public Admin(String adminID, String name, String email, String permissions) {
        this.adminID = adminID;
        this.name = name;
        this.email = email;
        this.permissions = permissions;
        this.createdPlaces = new ArrayList<>();
        this.assignedReports = new ArrayList<>();
        this.isActive = true;
        this.actionLog = "";
    }
    public void approvePlaces(Place place){
        System.out.println("Approving place:"+place.getName());
        this.createdPlaces.add(place);
        actionLog = "Approved place: " + place.getName();
    }
    public void deleteUser(User user){
        System.out.println("Deleting User:"+user.getName());
        actionLog="Deleted User:"+user.getName();
    }
    public void viewReports() {
        System.out.println("Assigned reports: " + assignedReports);
        actionLog = "Viewed reports";
    }
    public void editAnyPlace(Place place, String newDescription) {
        System.out.println("Editing any place:"+place.getName());
        place.setDescription(newDescription);
        actionLog = "Edited place:"+place.getName();
    }


    public void removeReview(RatingReview review) {
            System.out.println("Removing review by: " + review.getReviewerName());
            actionLog = "Removed review by: " + review.getReviewerName();
    }
    public String getAdminID() {return adminID;}
    public void setAdminID(String adminID) {this.adminID = adminID;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPermissions() {return permissions;}
    public void setPermissions(String permissions) {this.permissions = permissions;}
    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {this.isActive = active;}
    public List<Place> getCreatedPlaces() {return createdPlaces;}
    public String getActionLog() {return actionLog;}
    public void setActionLog(String actionLog) {this.actionLog = actionLog;}
    public List<Report> getAssignedReports() {return assignedReports;}

}
