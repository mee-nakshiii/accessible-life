package com.accessiblelife.api;

import com.accessiblelife.model.User;
import com.accessiblelife.model.Place;
import com.accessiblelife.model.RatingReview;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.util.List;

public class ApiClient {

    public static boolean login(String email, String password) {
        try {
            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String json = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
            }

            return conn.getResponseCode() == 200;
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
    }

    public static boolean register(User user) {
        // Stub: register user
        System.out.println("Registering user: " + user.getEmail());
        return true;
    }

    public static boolean addReview(int placeId, RatingReview review) {
        // Stub: add review to place
        System.out.println("Adding review for place ID " + placeId + ": " + review.getReviewText());
        return true;
    }

    public static List<Place> searchPlaces(String query, Boolean accessibleOnly) {
        // Stub: return dummy list
        System.out.println("Searching places with query: " + query + ", accessibleOnly: " + accessibleOnly);
        return List.of(new Place("Sample Place", "123 Main Street"));
    }
}