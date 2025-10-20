package com.accessiblelife.service;

import com.accessiblelife.model.Place;
import com.accessiblelife.repository.PlaceRepository;

import java.util.List;

public class PlaceService {
    private final PlaceRepository placeRepository = new PlaceRepository();

    public List<Place> getAllPlaces() {
        return placeRepository.getAllPlaces();
    }

    public List<Place> searchPlacesByFeatures(boolean ramp, boolean toilet, boolean braille, boolean elevator) {
        return placeRepository.searchPlacesByFeatures(ramp, toilet, braille, elevator);
    }

    // NEW METHOD: Save a new place (called from the GUI)
    public boolean savePlace(Place place) {
        // Basic validation before saving
        if (place.getName().trim().isEmpty() || place.getLocation().trim().isEmpty()) {
            System.err.println("Place name and location cannot be empty.");
            return false;
        }
        return placeRepository.savePlace(place);
    }
}