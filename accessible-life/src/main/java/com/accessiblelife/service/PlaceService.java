package com.accessiblelife.service;

import com.accessiblelife.model.Place;
import com.accessiblelife.model.User;
import com.accessiblelife.repository.PlaceRepository;
import com.accessiblelife.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    // Add a place
    public Place addPlace(Place place, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            place.setAddedBy(user);

            // Set default values for nullable fields if null
            if (place.getHasRamp() == null) {
                place.setHasRamp(false);
            }
            if (place.getIsVerified() == null) {
                place.setVerified(false);
            }
            if (place.getVerificationVote() == null) {
                place.setVerificationVote(0);
            }
            if (place.getHasAccessibleToilet() == null) {
                place.setHasAccessibleToilet(false);
            }
            if (place.getHasBrailleSignage() == null) {    // <-- Add this
                place.setHasBrailleSignage(false);
            }
            return placeRepository.save(place);
        }
        return null;
    }

    // Get all places
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    // Search places by name
    public List<Place> searchPlaces(String name) {
        return placeRepository.findByPlaceNameContainingIgnoreCase(name);
    }

    // Get only accessible places (with ramps)
    public List<Place> getAccessiblePlaces() {
        return placeRepository.findAll()
                .stream()
                .filter(place -> place.getHasRamp() != null && place.getHasRamp())
                .collect(Collectors.toList());
    }

    // Approve place
    /*public Place approvePlace(Long placeId) {
        Place place = placeRepository.findById(placeId).orElse(null);
        if (place != null) {
            place.setVerified(true); // correct setter
            return placeRepository.save(place);
        }
        return null;
    }*/
    public Place approvePlace(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + id));

        place.setVerified(true); // mark it as approved
        return placeRepository.save(place);
    }

}
