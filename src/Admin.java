import java.util.ArrayList;
import java.util.List;
public class Admin extends User{
    private String adminID;
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
        super(name,email);
        this.adminID = adminID;
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
        place.setshortDescription(newDescription);
        actionLog = "Edited place:"+place.getName();
    }


    public void removeReview(RatingReview review) {
        System.out.println("Removing review by: " + review.getUser());
        actionLog = "Removed review by: " + review.getUser();
    }
    public String getAdminID() {return adminID;}
    public void setAdminID(String adminID) {this.adminID = adminID;}
    public String getPermissions() {return permissions;}
    public void setPermissions(String permissions) {this.permissions = permissions;}
    public boolean isActive() {return isActive;}
    public void setActive(boolean active) {this.isActive = active;}
    public List<Place> getCreatedPlaces() {return createdPlaces;}
    public String getActionLog() {return actionLog;}
    public void setActionLog(String actionLog) {this.actionLog = actionLog;}
    public List<Report> getAssignedReports() {return assignedReports;}

}
