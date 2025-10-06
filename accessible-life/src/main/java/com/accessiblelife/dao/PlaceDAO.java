package com.accessiblelife.dao;

import com.accessiblelife.model.Place;
import com.accessiblelife.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaceDAO {

    @Autowired
    private PlaceRepository placeRepository;

    public List<Place> searchPlacesByName(String name) {
        // Corrected repository method call
        return placeRepository.findByPlaceNameContainingIgnoreCase(name);
    }

    public Place getPlaceById(Long placeId) {
        // Ensure ID type matches entity (Long)
        return placeRepository.findById(placeId).orElse(null);
    }

    public Place savePlace(Place place) {
        return placeRepository.save(place);
    }
}
