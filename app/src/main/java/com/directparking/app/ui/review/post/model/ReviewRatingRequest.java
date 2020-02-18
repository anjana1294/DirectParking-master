package com.directparking.app.ui.review.post.model;

/**
 * Created by root on 10/8/18.
 */

public class ReviewRatingRequest {
    private String userId;
    private String relatedId;
    private String rating;
    private String comment;
    private String rideId;

    public ReviewRatingRequest(String userId, String relatedId,
                               String rating, String comment, String rideId) {
        this.userId = userId;
        this.relatedId = relatedId;
        this.rating = rating;
        this.comment = comment;
        this.rideId = rideId;
    }
}
