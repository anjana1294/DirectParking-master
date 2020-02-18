package com.directparking.app.ui.chat.chatlist;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.chat.chatlist.model.ChatItem;
import com.directparking.app.ui.chat.chatlist.model.ChatListRequest;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by root on 29/8/18.
 */

public class ChatInteractorImpl implements ChatInteractor {
    private RestService restService;
    private UserManager userManager;

    public ChatInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<List<ChatItem>> fetchChats() {
        return restService.chatList(new ChatListRequest(userManager.getUserId()))
                .map(ChatParser::parse);
    }
}
