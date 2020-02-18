package com.directparking.app.ui.chat.messages;

import android.support.annotation.NonNull;

import com.directparking.app.ui.chat.messages.model.MessageItem;
import com.directparking.app.ui.chat.messages.model.MessageListResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class MessageParser {

    @NonNull
    static List<MessageItem> parse(Response<MessageListResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            MessageListResponse body = response.body();

            if (body.isStatus()) {
                List<MessageItem> messageItems = body.getData();
                if (messageItems != null && !messageItems.isEmpty()) {
                    return messageItems;
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