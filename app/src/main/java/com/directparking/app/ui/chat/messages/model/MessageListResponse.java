package com.directparking.app.ui.chat.messages.model;

import java.util.List;

/**
 * Created by root on 29/8/18.
 */

public class MessageListResponse {
    private String msg;
    private boolean status;
    private List<MessageItem> data;

    public String getMsg() {
        return msg;
    }

    public boolean isStatus() {
        return status;
    }

    public List<MessageItem> getData() {
        return data;
    }
}
