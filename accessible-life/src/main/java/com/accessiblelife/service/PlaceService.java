package com.accessiblelife.service;

import com.accessiblelife.model.Place;
import com.accessiblelife.repository.PlaceRepository;

import java.util.List;

public class PlaceService {
    private final PlaceRepository placeRepository = new PlaceRepository();

    public List<Place> getAllPlaces() {
        return placeRepository.getAllPlaces();
    }

    // FIX: Add the missing method signature that SearchByFeature.java requires
    public List<Place> searchPlacesByFeatures(boolean ramp, boolean toilet, boolean braille, boolean elevator) {
        return placeRepository.searchPlacesByFeatures(ramp, toilet, braille, elevator);
    }

    public Place getPlaceById(long placeId) {
        return placeRepository.getPlaceById(placeId);
    }

    public List<Place> getPlacesByUserId(long userId) {
        return placeRepository.getPlacesByUserId(userId);
    }

    public boolean savePlace(Place place) {
        if (place.getName().trim().isEmpty() || place.getLocation().trim().isEmpty()) {
            System.err.println("Place name and location cannot be empty.");
            return false;
        }
        return placeRepository.savePlace(place);
    }

    public boolean updatePlace(Place place) {
        // This relies on the placeholder method in the repository.
        return placeRepository.updatePlace(place);
    }

    public boolean deletePlace(long placeId) {
        if (placeId <= 0) {
            System.err.println("Invalid Place ID for deletion.");
            return false;
        }
        return placeRepository.deletePlace(placeId);
    }
}