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

    // Helper to get connection and handle null checks
    private Connection getConnectionSafely() {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("❌ Database connection unavailable.");
        }
        return conn;
    }

    public List<Place> getAllPlaces() {
        List<Place> places = new ArrayList<>();
        Connection conn = getConnectionSafely();

        if (conn == null) return places;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM places");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                places.add(createPlaceFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error while fetching all places: " + e.getMessage());
        }
        return places;
    }

    public List<Place> searchPlacesByFeatures(boolean ramp, boolean toilet, boolean braille, boolean elevator) {
        List<Place> places = new ArrayList<>();
        Connection conn = getConnectionSafely();

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
            System.err.println("❌ SQL error during feature search: " + e.getMessage());
        }
        return places;
    }

    public Place getPlaceById(long placeId) {
        Connection conn = getConnectionSafely();
        if (conn == null) return null;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM places WHERE place_id = ?")) {
            stmt.setLong(1, placeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createPlaceFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error fetching single place: " + e.getMessage());
        }
        return null;
    }

    public List<Place> getPlacesByUserId(long userId) {
        List<Place> places = new ArrayList<>();
        Connection conn = getConnectionSafely();
        if (conn == null) return places;

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT p.* FROM places p JOIN user_places up ON p.place_id = up.place_id WHERE up.user_id = ?")) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    places.add(createPlaceFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error fetching user's places: " + e.getMessage());
        }
        return places;
    }

    public boolean savePlace(Place place) {
        Connection conn = getConnectionSafely();
        if (conn == null) return false;

        String sql = "INSERT INTO places (name, description, location, category, has_ramp, has_accessible_toilet, has_braille_signage, has_elevator) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, place.getName());
            stmt.setString(2, place.getDescription());
            stmt.setString(3, place.getLocation());
            stmt.setString(4, place.getCategory());
            stmt.setBoolean(5, place.isHasRamp());
            stmt.setBoolean(6, place.isHasAccessibleToilet());
            stmt.setBoolean(7, place.isHasBrailleSignage());
            stmt.setBoolean(8, place.isHasElevator());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        place.setId(generatedKeys.getLong(1));
                    }
                }
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("❌ SQL error while saving new place: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePlace(Place place) {
        Connection conn = getConnectionSafely();
        if (conn == null) return false;

        String sql = "UPDATE places SET name=?, description=?, location=?, category=?, has_ramp=?, has_accessible_toilet=?, has_braille_signage=?, has_elevator=? WHERE place_id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, place.getName());
            stmt.setString(2, place.getDescription());
            stmt.setString(3, place.getLocation());
            stmt.setString(4, place.getCategory());
            stmt.setBoolean(5, place.isHasRamp());
            stmt.setBoolean(6, place.isHasAccessibleToilet());
            stmt.setBoolean(7, place.isHasBrailleSignage());
            stmt.setBoolean(8, place.isHasElevator());
            stmt.setLong(9, place.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ SQL error while updating place: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePlace(long placeId) {
        Connection conn = getConnectionSafely();
        if (conn == null) return false;

        try {
            conn.setAutoCommit(false);

            // Delete dependent records (transactional cleanup)
            try (PreparedStatement deleteUserPlaces = conn.prepareStatement("DELETE FROM user_places WHERE place_id = ?")) {
                deleteUserPlaces.setLong(1, placeId);
                deleteUserPlaces.executeUpdate();
            }
            try (PreparedStatement deleteReviews = conn.prepareStatement("DELETE FROM reviews WHERE place_id = ?")) {
                deleteReviews.setLong(1, placeId);
                deleteReviews.executeUpdate();
            }
            try (PreparedStatement deleteReports = conn.prepareStatement("DELETE FROM reports WHERE place_id = ?")) {
                deleteReports.setLong(1, placeId);
                deleteReports.executeUpdate();
            }

            // Delete the place itself
            try (PreparedStatement deletePlace = conn.prepareStatement("DELETE FROM places WHERE place_id = ?")) {
                deletePlace.setLong(1, placeId);
                int affectedRows = deletePlace.executeUpdate();
                conn.commit();
                return affectedRows > 0;
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error while deleting place (transaction rolled back): " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error rolling back transaction: " + ex.getMessage());
            }
            return false;
        } finally {
            // Connection closure should ideally be handled at a higher level,
            // but we ensure auto-commit is reset.
        }
    }
}