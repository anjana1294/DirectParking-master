package com.directparking.app.ui.signup.model;

import java.util.List;

public class UniversityResponse {

    private boolean status;
    private String msg;
    private List<IdNameItem> data;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<IdNameItem> getData() {
        return data;
    }
}