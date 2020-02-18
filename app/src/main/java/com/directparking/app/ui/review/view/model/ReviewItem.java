package com.directparking.app.ui.review.view.model;

/**
 * Created by root on 30/8/18.
 */

public class ReviewItem {
    private String userid;
    private String fname;
    private String lname;

    private String comment;
    private String created;
    private String profile;
    private int rating;

    public ReviewItem(String userid, String fname, String lname, String comment, String created, String profile,
                      int rating) {
        this.userid = userid;
        this.fname = fname;
        this.lname = lname;
        this.comment = comment;
        this.created = created;
        this.profile = profile;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public ReviewItem setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public ReviewItem setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getFname() {
        return fname;
    }

    public ReviewItem setFname(String fname) {
        this.fname = fname;
        return this;
    }

    public String getLname() {
        return lname;
    }

    public ReviewItem setLname(String lname) {
        this.lname = lname;
        return this;
    }

    public String getReview() {
        return comment;
    }

    public ReviewItem setReview(String comment) {
        this.comment = comment;
        return this;
    }

    public String getTime() {
        return created;
    }

    public ReviewItem setTime(String created) {
        this.created = created;
        return this;
    }

    public String getProfile() {
        return profile;
    }

    public ReviewItem setProfile(String profile) {
        this.profile = profile;
        return this;
    }
}
