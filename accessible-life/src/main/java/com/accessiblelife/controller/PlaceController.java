package com.accessiblelife.controller;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    // Add a new place
    @PostMapping("/add/{userId}")
    public Place addPlace(@RequestBody Place place, @PathVariable Long userId) {
        return placeService.addPlace(place, userId);
    }

    // Get all places
    @GetMapping("/all")
    public List<Place> getAllPlaces() {
        return placeService.getAllPlaces();
    }

    // Search places by name (optional filter by ramp)
    @GetMapping("/search")
    public List<Place> searchPlaces(@RequestParam String name,
                                    @RequestParam(required = false) Boolean hasRamp) {
        List<Place> results = placeService.searchPlaces(name);
        if (hasRamp != null) {
            results = results.stream()
                    .filter(place -> place.getHasRamp() != null && place.getHasRamp() == hasRamp)
                    .collect(Collectors.toList());
        }
        return results;
    }

    // Get only accessible places (with ramp)
    @GetMapping("/accessible")
    public List<Place> getAccessiblePlaces() {
        return placeService.getAccessiblePlaces();
    }


    @PatchMapping("/approve/{id}")  // PATCH is better for partial update
    public Place approvePlace(@PathVariable Long id) {
        return placeService.approvePlace(id);
    }


}
