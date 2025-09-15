import java.util.ArrayList;
import java.util.List;

public class Place {


    private List<Place> accessiblePlaceNearby;
    private String location;
    private String specificAddress;
    private String shortDescription;
    private String featuresAvailable;
    private boolean addNewPlace;
    private boolean deletePlace;
    private boolean hasRamp;
    private boolean hasElevator;
    private boolean hasBrailleSignage;
    private boolean hasAccessibleToilet;
    private boolean isVerified;
    private int verificationVote;
    private int reportCount;


    public Place() {
        this.accessiblePlaceNearby = new ArrayList<>();
        this.featuresAvailable = "";
        this.addNewPlace = false;
        this.deletePlace = false;
        this.hasRamp = false;
        this.hasElevator = false;
        this.hasBrailleSignage = false;
        this.hasAccessibleToilet = false;
        this.isVerified = false;
        this.verificationVote = 0;
        this.reportCount = 0;
    }

    public Place(String location, String specificAddress, String shortDescription, String featuresAvailable) {
        this();
        this.location = location;
        this.specificAddress = specificAddress;
        this.shortDescription = shortDescription;
        this.featuresAvailable = featuresAvailable;
    }


    public void viewDetails() {
        System.out.println("----- Place Details -----");
        System.out.println("Location: " + location);
        System.out.println("Address: " + specificAddress);
        System.out.println("Description: " + shortDescription);
        System.out.println("Features: " + featuresAvailable);
        System.out.println("Has Ramp: " + hasRamp);
        System.out.println("Has Elevator: " + hasElevator);
        System.out.println("Has Braille Signage: " + hasBrailleSignage);
        System.out.println("Accessible Toilet: " + hasAccessibleToilet);
        System.out.println("Verified: " + isVerified);
        System.out.println("Verification Votes: " + verificationVote);
        System.out.println("Reports: " + reportCount);
        System.out.println("------------------------");
    }


    public void addPlace() {
        this.addNewPlace = true;
        System.out.println("Place successfully added: " + shortDescription);
    }


    public void editPlace(String newDescription, String newFeatures) {
        this.shortDescription = newDescription;
        this.featuresAvailable = newFeatures;
        System.out.println("Place updated successfully: " + shortDescription);
    }


    public void deletePlace() {
        this.deletePlace = true;
        System.out.println("Place deleted successfully: " + shortDescription);
    }


    public void verifyPlace() {
        this.isVerified = true;
        this.verificationVote++;
        System.out.println("Place verified successfully: " + shortDescription);
    }


    public void reportPlace(String reason) {
        this.reportCount++;
        System.out.println("Place reported. Reason: " + reason);
    }



    public List<Place> getAccessiblePlaceNearby() {
        return accessiblePlaceNearby;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setAccessiblePlaceNearby(List<Place> accessiblePlaceNearby) {
        this.accessiblePlaceNearby = accessiblePlaceNearby;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecificAddress() {
        return specificAddress;
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public String getshortDescription() {
        return shortDescription;
    }

    public void setshortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFeaturesAvailable() {
        return featuresAvailable;
    }

    public void setFeaturesAvailable(String featuresAvailable) {
        this.featuresAvailable = featuresAvailable;
    }

    public boolean hasRamp() {
        return hasRamp;
    }

    public void setHasRamp(boolean hasRamp) {
        this.hasRamp = hasRamp;
    }

    public boolean hasElevator() {
        return hasElevator;
    }

    public void setHasElevator(boolean hasElevator) {
        this.hasElevator = hasElevator;
    }

    public boolean hasBrailleSignage() {
        return hasBrailleSignage;
    }

    public void setHasBrailleSignage(boolean hasBrailleSignage) {
        this.hasBrailleSignage = hasBrailleSignage;
    }

    public boolean hasAccessibleToilet() {
        return hasAccessibleToilet;
    }

    public void setHasAccessibleToilet(boolean hasAccessibleToilet) {
        this.hasAccessibleToilet = hasAccessibleToilet;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public int getVerificationVote() {
        return verificationVote;
    }

    public void setVerificationVote(int verificationVote) {
        this.verificationVote = verificationVote;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }
}
