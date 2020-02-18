package com.directparking.app.ui.chat.chatlist;

import com.directparking.app.ui.chat.chatlist.model.ChatItem;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by root on 29/8/18.
 */

public interface ChatInteractor {
    Single<List<ChatItem>> fetchChats();

}
