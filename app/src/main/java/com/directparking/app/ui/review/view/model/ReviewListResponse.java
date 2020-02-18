package com.directparking.app.ui.review.view.model;

import java.util.List;

/**
 * Created by root on 30/8/18.
 */

public class ReviewListResponse {
    private String msg;
    private boolean status;
    private List<ReviewItem> data;

    public String getMsg() {
        return msg;
    }

    public boolean isStatus() {
        return status;
    }

    public List<ReviewItem> getData() {
        return data;
    }

}
