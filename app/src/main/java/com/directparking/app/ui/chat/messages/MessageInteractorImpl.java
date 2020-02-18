package com.directparking.app.ui.chat.messages;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.chat.messages.model.MessageItem;
import com.directparking.app.ui.chat.messages.model.MessageListRequest;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by root on 29/8/18.
 */

public class MessageInteractorImpl implements MessageInteractor {
    private RestService restService;
    private UserManager userManager;

    public MessageInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<List<MessageItem>> fetchMessages(String rideId) {
        return restService.messageList(new MessageListRequest(rideId))
                .map(MessageParser::parse);
    }

    @Override
    public Single<String> sendMessage(MessageItem message) {
        return restService.sendMesssage(message)
                .map(SendMessageParser::parse);
    }
}
