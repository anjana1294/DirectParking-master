package com.directparking.app.ui.chat.messages.di;

import com.directparking.app.ui.chat.messages.MessageActivity;

import dagger.Subcomponent;

/**
 * Created by root on 24/7/18.
 */

@MessageScope
@Subcomponent(modules = {MessageModule.class})
public interface MessageComponent {
    void inject(MessageActivity activity);
}
