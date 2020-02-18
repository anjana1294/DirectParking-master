package com.directparking.app.ui.history;

import com.directparking.app.ui.base.BasePresenter;

/**
 * Created by root on 21/8/18.
 */

public abstract class HistoryPresenter extends BasePresenter<HistoryView> {
    abstract void fetchHistory();
}
