package com.directparking.app.ui.history;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.history.model.HistoryRideResponse;

/**
 * Created by root on 10/8/18.
 */

public interface HistoryView extends BaseView {
    void showHistoryList(HistoryRideResponse historyRideData);
}
