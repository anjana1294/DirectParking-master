package com.directparking.app.ui.accept.model;

import java.util.List;

public class AcceptListResponse {

    private String msg;
    private boolean status;
    private List<AcceptItem> data;

    public boolean isStatus() {
        return status;
    }

    public List<AcceptItem> getAcceptItems() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}