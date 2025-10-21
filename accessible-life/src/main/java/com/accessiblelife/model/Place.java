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

    // 1. Full 9-argument constructor (used when fetching from DB)
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

    // 2. CRITICAL FIX: 8-argument constructor for NEW places (ID is 0 for DB auto-generation)
    // This resolves the "Cannot resolve constructor" error.
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

    // Feature Getters
    public boolean isHasRamp() { return hasRamp; }
    public boolean isHasAccessibleToilet() { return hasAccessibleToilet; }
    public boolean isHasBrailleSignage() { return hasBrailleSignage; }
    public boolean isHasElevator() { return hasElevator; }
}