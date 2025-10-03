package com.accessiblelife.model;

import java.util.List;
    public class User {
        private String name;
        private String userID;
        private String email;
        private String password;
        private String location;
        private List<Place> savedPlaces;
        private List<Place> visitedPlaces;


        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }


        public User() {
            // Initialize fields with default values
            this.name = "";
            this.userID = "";
            this.email = "";
            this.password = "";
            this.location = "";
        }


        public User(String name, String userID, String email, String password, String location, List<Place> savedPlaces, List<Place> visitedPlaces) {
            this.name = name;
            this.userID = userID;
            this.email = email;
            this.password = password;
            this.location = location;
            this.savedPlaces = savedPlaces;
            this.visitedPlaces = visitedPlaces;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<Place> getSavedPlaces() {
            return savedPlaces;
        }

        public void setSavedPlaces(List<Place> savedPlaces) {
            this.savedPlaces = savedPlaces;
        }

        public List<Place> getVisitedPlaces() {
            return visitedPlaces;
        }

        public void setVisitedPlaces(List<Place> visitedPlaces) {
            this.visitedPlaces = visitedPlaces;
        }


        public boolean login(String email, String password) {
            return this.email.equals(email) && this.password.equals(password);
        }

        public boolean signup(String email, String name, String userID, String password) {
            // Placeholder for signup logic
            return true;
        }


        public void viewProfile() {
            System.out.println("Viewing profile for user: " + this.name);
        }

        public void addPlace() {

            System.out.println("Adding a new place...");
        }

        public void savePlace(Place place) {

            if (this.savedPlaces != null) {
                this.savedPlaces.add(place);
            }
        }

        public void visitedPlace(Place place) {
            if (this.visitedPlaces != null) {
                this.visitedPlaces.add(place);
            }
        }

        public void reportPlace(Place place, String reason) {

            System.out.println("Reporting place: " + place.getName() + " for reason: " + reason);
        }
    }

    class Place {
        private String name;

        public Place(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

