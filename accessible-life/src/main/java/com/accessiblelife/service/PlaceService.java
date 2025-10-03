package com.accessiblelife.service;

import com.accessiblelife.dao.PlaceDAO;
import com.accessiblelife.model.Place;
import java.util.List;

public class PlaceService {

    private PlaceDAO placeDAO;

    public PlaceService() {
        this.placeDAO = new PlaceDAO();
    }

    /**
     * Add a new place submitted by a user
     * @param place Place object
     * @param userId ID of the user adding the place
     * @return true if successfully added
     */
    public boolean addPlace(Place place, int userId) {
        if (place == null || place.getName() == null || place.getSpecificAddress() == null) {
            return false; // validation
        }
        return placeDAO.add(place, userId);
    }

    /**
     * Search places by name and optional ramp filter
     * @param partialName partial name for search
     * @param hasRamp Boolean filter for ramp availability (true/false), null to ignore
     * @return list of matching places
     */
    public List<Place> searchPlaces(String partialName, Boolean hasRamp) {
        if (partialName == null) partialName = "";
        return placeDAO.search(partialName, hasRamp);
    }

    /**
     * Approve a place by incrementing its verification vote
     * @param placeId ID of the place
     * @return true if successful
     */
    public boolean approvePlace(int placeId) {
        return placeDAO.approve(placeId);
    }
}
