package com.accessiblelife.model;

public class Place {
    private long id;
    private String name;
    private String description;
    private String location;
    private String category;

    private boolean hasRamp;
    private boolean hasAccessibleToilet;
    private boolean hasBrailleSignage;
    private boolean hasElevator;

    // Full 9-argument constructor (used when fetching from DB)
    public Place(long id, String name, String description, String location, String category,
                 boolean hasRamp, boolean hasAccessibleToilet, boolean hasBrailleSignage, boolean hasElevator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.category = category;
        this.hasRamp = hasRamp;
        this.hasAccessibleToilet = hasAccessibleToilet;
        this.hasBrailleSignage = hasBrailleSignage;
        this.hasElevator = hasElevator;
    }

    // 8-argument constructor for NEW places
    public Place(String name, String description, String location, String category,
                 boolean hasRamp, boolean hasAccessibleToilet, boolean hasBrailleSignage, boolean hasElevator) {
        this(0, name, description, location, category, hasRamp, hasAccessibleToilet, hasBrailleSignage, hasElevator);
    }

    // Minimal constructor
    public Place(String name, String description, String location, String category) {
        this(0, name, description, location, category, false, false, false, false);
    }

    // Getters
    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getCategory() { return category; }

    // NEW SETTER (Resolves 'Cannot resolve method setId' error)
    public void setId(long id) {
        this.id = id;
    }

    // Feature Getters
    public boolean isHasRamp() { return hasRamp; }
    public boolean isHasAccessibleToilet() { return hasAccessibleToilet; }
    public boolean isHasBrailleSignage() { return hasBrailleSignage; }
    public boolean isHasElevator() { return hasElevator; }
}