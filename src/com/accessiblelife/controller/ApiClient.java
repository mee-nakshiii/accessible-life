package com.accessiblelife.controller;

public class ApiClient
{
    public static String approvePlace(int placeId)
    {
        // TODO: Replace with REST API call
        System.out.println("Pretend approving place: " + placeId);
        return "Place " + placeId + " approved!";
    }

    public static String rejectPlace(int placeId)
    {
        // TODO: Replace with REST API call
        System.out.println("Pretend rejecting place: " + placeId);
        return "Place " + placeId + " rejected!";
    }
}
