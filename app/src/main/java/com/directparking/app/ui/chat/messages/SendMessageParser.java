package com.directparking.app.ui.chat.messages;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.directparking.app.ui.chat.messages.model.SendMessageResponse;

import java.io.IOException;

import retrofit2.Response;

public class SendMessageParser {

    @NonNull
    static String parse(Response<SendMessageResponse> response) throws IOException, NullPointerException {

        if (response.isSuccessful()) {
            SendMessageResponse body = response.body();

            if (body.isStatus()) {
                String msg = body.getMsg();
                if (!TextUtils.isEmpty(msg)) {
                    return msg;
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