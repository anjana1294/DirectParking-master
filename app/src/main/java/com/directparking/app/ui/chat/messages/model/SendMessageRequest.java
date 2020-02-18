package com.directparking.app.ui.chat.messages.model;

/**
 * Created by root on 31/8/18.
 */

public class SendMessageRequest {

    private String message;
    private String created;
    private String fromUserId;
    private String toUserId;
    private String rideId;

    public SendMessageRequest(String message, String created, String fromUserId,
                              String toUserId, String rideId) {
        this.message = message;
        this.created = created;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.rideId = rideId;
    }
}
