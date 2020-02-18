package com.directparking.app.ui.chat.messages;

import com.directparking.app.ui.chat.messages.model.MessageItem;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by root on 29/8/18.
 */

public interface MessageInteractor {
    Single<List<MessageItem>> fetchMessages(String rideId);

    Single<String> sendMessage(MessageItem message);

}
