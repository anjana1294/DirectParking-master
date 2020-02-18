package com.directparking.app.ui.chat.messages;

import com.directparking.app.ui.chat.messages.model.MessageItem;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 29/8/18.
 */

public class MessagePresenterImpl extends MessagePresenter {

    MessageView view;
    MessageInteractor interactor;

    public MessagePresenterImpl(MessageInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(MessageView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void fetchMessageList(String rideId) {
        showProgressBar();
        disposable = interactor.fetchMessages(rideId)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(chats -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showMessageList(chats);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                    showEmptyView();
                });
    }

    @Override
    void sendMessage(MessageItem message) {
        showProgressBar();
        disposable = interactor.sendMessage(message)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(msg -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.updateMessageList(message);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                });
    }

}
