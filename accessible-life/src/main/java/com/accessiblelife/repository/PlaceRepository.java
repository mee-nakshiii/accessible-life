package com.accessiblelife.repository;

import com.accessiblelife.db.DatabaseManager;
import com.accessiblelife.model.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {

    private Place createPlaceFromResultSet(ResultSet rs) throws SQLException {
        return new Place(
                rs.getLong("place_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getString("category"),
                rs.getBoolean("has_ramp"),
                rs.getBoolean("has_accessible_toilet"),
                rs.getBoolean("has_braille_signage"),
                rs.getBoolean("has_elevator")
        );
    }

    public List<Place> getAllPlaces() {
        // ... (existing getAllPlaces method) ...
        List<Place> places = new ArrayList<>();
        Connection conn = DatabaseManager.getConnection();

        if (conn == null) return places;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM places");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                places.add(createPlaceFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error while fetching places:");
            e.printStackTrace();
        }

        return places;
    }

    // ... (existing searchPlacesByFeatures method) ...
    public List<Place> searchPlacesByFeatures(boolean ramp, boolean toilet, boolean braille, boolean elevator) {
        List<Place> places = new ArrayList<>();
        Connection conn = DatabaseManager.getConnection();

        if (conn == null) return places;

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM places WHERE 1=1");

        if (ramp) queryBuilder.append(" AND has_ramp = TRUE");
        if (toilet) queryBuilder.append(" AND has_accessible_toilet = TRUE");
        if (braille) queryBuilder.append(" AND has_braille_signage = TRUE");
        if (elevator) queryBuilder.append(" AND has_elevator = TRUE");

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryBuilder.toString())) {

            while (rs.next()) {
                places.add(createPlaceFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error during feature search:");
            e.printStackTrace();
        }

        return places;
    }

    // NEW METHOD: Save a new place to the database
    public boolean savePlace(Place place) {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) return false;

        String sql = "INSERT INTO places (name, description, location, category, has_ramp, has_accessible_toilet, has_braille_signage, has_elevator) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, place.getName());
            stmt.setString(2, place.getDescription());
            stmt.setString(3, place.getLocation());
            stmt.setString(4, place.getCategory());
            stmt.setBoolean(5, place.isHasRamp());
            stmt.setBoolean(6, place.isHasAccessibleToilet());
            stmt.setBoolean(7, place.isHasBrailleSignage());
            stmt.setBoolean(8, place.isHasElevator());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ SQL error while saving new place:");
            e.printStackTrace();
            return false;
        }
    }
}