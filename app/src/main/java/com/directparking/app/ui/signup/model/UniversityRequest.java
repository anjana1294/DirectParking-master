package com.directparking.app.ui.signup.model;

public class UniversityRequest {

    private String userId;
    private String name;
    private String searchText;

    public UniversityRequest(String userId) {
        this.userId = userId;
    }


    public UniversityRequest(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}