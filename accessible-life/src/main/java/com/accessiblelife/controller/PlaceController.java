package com.accessiblelife.controller;

import com.accessiblelife.model.Place;
import com.accessiblelife.service.PlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private PlaceService placeService = new PlaceService();

    @PostMapping("/add")
    public boolean addPlace(@RequestBody Place place, @RequestParam int userId) {
        return placeService.addPlace(place, userId);
    }

    @GetMapping("/search")
    public List<Place> search(@RequestParam String name,
                              @RequestParam(required = false) Boolean hasRamp) {
        return placeService.searchPlaces(name, hasRamp);
    }

    @PostMapping("/approve")
    public boolean approve(@RequestParam int placeId) {
        return placeService.approvePlace(placeId);
    }
}
