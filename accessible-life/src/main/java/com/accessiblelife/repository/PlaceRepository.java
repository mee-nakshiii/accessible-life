package com.accessiblelife.repository;

import com.accessiblelife.db.DatabaseManager;
import com.accessiblelife.model.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {

    public List<Place> getAllPlaces() {
        List<Place> places = new ArrayList<>();
        Connection conn = DatabaseManager.getConnection();

        if (conn == null) {
            System.err.println("❌ Connection is null. Cannot fetch places.");
            return places;
        }

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM places");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Place place = new Place(
                        rs.getLong("place_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("category")
                );
                places.add(place);
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error while fetching places:");
            e.printStackTrace();
        }

        return places;
    }
}