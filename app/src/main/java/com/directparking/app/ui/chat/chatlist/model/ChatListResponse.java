package com.directparking.app.ui.chat.chatlist.model;

import java.util.List;

/**
 * Created by root on 29/8/18.
 */

public class ChatListResponse {
    private String msg;
    private boolean status;
    private List<ChatItem> data;

    public String getMsg() {
        return msg;
    }

    public boolean isStatus() {
        return status;
    }

    public List<ChatItem> getData() {
        return data;
    }
}
