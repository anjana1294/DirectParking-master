package com.directparking.app.ui.chat.chatlist;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 29/8/18.
 */

public class ChatPresenterImpl extends ChatPresenter {

    ChatView view;
    ChatInteractor interactor;

    public ChatPresenterImpl(ChatInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(ChatView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void fetchChatList() {
        showProgressBar();
        disposable = interactor.fetchChats()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(chats -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showChatList(chats);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                    showEmptyView();
                });
    }
}
