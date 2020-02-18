package com.directparking.app.ui.confrimation.model;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by root on 26/7/18.
 */

public class ConfirmationResponse {

    private String msg;

    public int getSuccess() {
        return success;
    }

    private int success;


    public void setData(Data data) {
        this.data = data;
    }

    private Data data;
    private boolean status;

    public Data getData() {
        return data;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
