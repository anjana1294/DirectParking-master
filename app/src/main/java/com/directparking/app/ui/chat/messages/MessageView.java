package com.directparking.app.ui.chat.messages;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.chat.messages.model.MessageItem;

import java.util.List;

/**
 * Created by root on 29/8/18.
 */

interface MessageView extends BaseView {
    void showMessageList(List<MessageItem> messageItems);

    void showSuccessMessage(String msg);
    void updateMessageList(MessageItem messageRequest);
}
