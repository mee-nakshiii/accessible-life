package com.accessiblelife.service;

import com.accessiblelife.model.Place;
import com.accessiblelife.repository.PlaceRepository;

import java.util.List;

public class PlaceService {
    private final PlaceRepository placeRepo = new PlaceRepository();

    public List<Place> fetchAllPlaces() {
        return placeRepo.getAllPlaces();
    }

    public Place createPlace(long id, String name, String description, String location, String category) {
        return new Place(id, name, description, location, category);
    }
}