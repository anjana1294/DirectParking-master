package com.directparking.app.ui.chat.chatlist;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.chat.chatlist.model.ChatItem;

import java.util.List;

/**
 * Created by root on 29/8/18.
 */

interface ChatView extends BaseView {
    void showChatList(List<ChatItem> chatItemList);

    void onItemClicked(ChatItem item);
}
