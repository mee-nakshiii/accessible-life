package com.accessiblelife.model;

public class Place {
    private long id;
    private String placeName;
    private String description;
    private String location;
    private String category;

    public Place(long id, String placeName, String description, String location, String category) {
        this.id = id;
        this.placeName = placeName;
        this.description = description;
        this.location = location;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return placeName; // or placeName, depending on your field
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getCategory() {
        return category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}