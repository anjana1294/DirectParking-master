package com.directparking.app.ui.history;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by root on 21/8/18.
 */

public class HistoryPresenterImpl extends HistoryPresenter {
    HistoryInteractor interactor;
    HistoryView view;

    public HistoryPresenterImpl(HistoryInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(HistoryView view) {
        super.setView(view);
        this.view = view;
    }

    @Override
    void fetchHistory() {
        showProgressBar();

        disposable = interactor.fetchHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(historyData -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showHistoryList(historyData);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                    showEmptyView();
                });
    }
}
