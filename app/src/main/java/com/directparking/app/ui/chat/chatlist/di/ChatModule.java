package com.directparking.app.ui.chat.chatlist.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.chat.chatlist.ChatInteractor;
import com.directparking.app.ui.chat.chatlist.ChatInteractorImpl;
import com.directparking.app.ui.chat.chatlist.ChatPresenter;
import com.directparking.app.ui.chat.chatlist.ChatPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 24/7/18.
 */
@Module
public class ChatModule {

    @Provides
    ChatInteractor provideChatInteractor(RestService restService, UserManager userManager) {
        return new ChatInteractorImpl(restService, userManager);
    }

    @Provides
    ChatPresenter provideChatPresenter(ChatInteractor interactor) {
        return new ChatPresenterImpl(interactor);
    }
}
