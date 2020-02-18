package com.directparking.app.ui.chat.chatlist.di;

import com.directparking.app.ui.chat.chatlist.ChatActivity;

import dagger.Subcomponent;

/**
 * Created by root on 24/7/18.
 */

@ChatScope
@Subcomponent(modules = {ChatModule.class})
public interface ChatComponent {
    void inject(ChatActivity activity);
}
