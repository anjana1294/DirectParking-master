package com.directparking.app.ui.chat.messages;

import com.directparking.app.ui.base.BasePresenter;
import com.directparking.app.ui.chat.messages.model.MessageItem;

/**
 * Created by root on 29/8/18.
 */

public abstract class MessagePresenter extends BasePresenter<MessageView> {
    abstract void fetchMessageList(String rideId);

    abstract void sendMessage(MessageItem message);
}
