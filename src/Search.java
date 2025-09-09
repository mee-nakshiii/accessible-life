import java.util.List;
import java.util.stream.Collectors;

public class Search {
    private List<Place> places;

    // Constructor to initialize the list of places
    public Search(List<Place> places) {
        this.places = places;
    }

    // Search by location (e.g., Kochi, Bangalore)
    public List<Place> searchByLocation(String location) {
        return places.stream()
                .filter(place -> place.getLocation() != null &&
                        place.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    // Search by specific accessibility feature
    public List<Place> searchByFeature(String feature) {
        return places.stream()
                .filter(place -> {
                    switch (feature.toLowerCase()) {
                        case "ramp": return place.hasRamp();
                        case "elevator": return place.hasElevator();
                        case "braille": return place.hasBrailleSignage();
                        case "toilet": return place.hasAccessibleToilet();
                        default: return false;
                    }
                })
                .collect(Collectors.toList());
    }

    // Search by keyword in short description or featuresAvailable
    public List<Place> searchByKeyword(String keyword) {
        return places.stream()
                .filter(place -> (place.getShortDescription() != null &&
                        place.getShortDescription().toLowerCase().contains(keyword.toLowerCase())) ||
                        (place.getFeaturesAvailable() != null &&
                                place.getFeaturesAvailable().toLowerCase().contains(keyword.toLowerCase())))
                .collect(Collectors.toList());
    }

    // Search for verified places only
    public List<Place> searchVerifiedPlaces() {
        return places.stream()
                .filter(Place::isVerified)
                .collect(Collectors.toList());
    }
}