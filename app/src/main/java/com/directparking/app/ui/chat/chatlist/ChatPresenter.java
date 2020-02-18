package com.directparking.app.ui.chat.chatlist;

import com.directparking.app.ui.base.BasePresenter;

/**
 * Created by root on 29/8/18.
 */

public abstract class ChatPresenter extends BasePresenter<ChatView> {
    abstract void fetchChatList();
}
