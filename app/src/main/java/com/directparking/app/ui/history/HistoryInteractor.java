package com.directparking.app.ui.history;

import com.directparking.app.ui.history.model.HistoryRide;
import com.directparking.app.ui.history.model.HistoryRideResponse;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by root on 21/8/18.
 */

public interface HistoryInteractor {
    Single<HistoryRideResponse> fetchHistory();

}
