package com.accessiblelife.dao;

import com.accessiblelife.db.DBConnectionHelper;
import com.accessiblelife.model.Place;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceDAO {

    // --- ADD (Insert a new place added by a user) ---
    public boolean add(Place place, int userId) {
        String sql = "INSERT INTO places (place_name, specific_address, has_ramp, added_by_user_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, place.getName());
            ps.setString(2, place.getSpecificAddress());
            ps.setBoolean(3, place.hasRamp());
            ps.setInt(4, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("PlaceDAO - Add failed: " + e.getMessage());
            return false;
        }
    }

    // --- SEARCH (Find places based on name and ramp status) ---
    public List<Place> search(String partialName, Boolean hasRamp) {
        List<Place> places = new ArrayList<>();

        String sql = "SELECT * FROM places WHERE place_name LIKE ?";
        if (hasRamp != null) {
            sql += " AND has_ramp = ?";
        }

        try (Connection conn = DBConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + partialName + "%");

            if (hasRamp != null) {
                ps.setBoolean(2, hasRamp);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Place place = new Place();
                    // Assuming place model can accept all these fields
                    // place.setPlaceId(rs.getInt("place_id")); // Needs PlaceID field in your model
                    place.setName(rs.getString("place_name"));
                    place.setSpecificAddress(rs.getString("specific_address"));
                    place.setHasRamp(rs.getBoolean("has_ramp"));
                    place.setVerified(rs.getBoolean("is_verified"));
                    place.setVerificationVote(rs.getInt("verification_vote"));
                    places.add(place);
                }
            }
        } catch (SQLException e) {
            System.err.println("PlaceDAO - Search failed: " + e.getMessage());
        }
        return places;
    }

    // --- APPROVE (Increment the verification vote) ---
    public boolean approve(int placeId) {
        String sql = "UPDATE places SET verification_vote = verification_vote + 1 WHERE place_id = ?";

        try (Connection conn = DBConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, placeId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("PlaceDAO - Approval failed: " + e.getMessage());
            return false;
        }
    }
}
