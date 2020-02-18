package com.directparking.app.ui.chat.messages.di;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.chat.messages.MessageInteractor;
import com.directparking.app.ui.chat.messages.MessageInteractorImpl;
import com.directparking.app.ui.chat.messages.MessagePresenter;
import com.directparking.app.ui.chat.messages.MessagePresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by root on 24/7/18.
 */
@Module
public class MessageModule {

    @Provides
    MessageInteractor provideMessageInteractor(RestService restService, UserManager userManager) {
        return new MessageInteractorImpl(restService,userManager);
    }

    @Provides
    MessagePresenter provideMessagePresenter(MessageInteractor interactor) {
        return new MessagePresenterImpl(interactor);
    }
}
