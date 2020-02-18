package com.directparking.app.ui.chat.chatlist;

import android.support.annotation.NonNull;

import com.directparking.app.ui.chat.chatlist.model.ChatItem;
import com.directparking.app.ui.chat.chatlist.model.ChatListResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class ChatParser {

    @NonNull
    static List<ChatItem> parse(Response<ChatListResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            ChatListResponse body = response.body();

            if (body.isStatus()) {
                List<ChatItem> chatItems = body.getData();
                if (chatItems != null && !chatItems.isEmpty()) {
                    return chatItems;
                } else {
                    throw new RuntimeException("Response payload is empty!");
                }
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}