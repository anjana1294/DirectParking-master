package com.directparking.app.ui.history;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.history.model.HistoryRideRequest;
import com.directparking.app.ui.history.model.HistoryRideResponse;

import io.reactivex.Single;

/**
 * Created by root on 21/8/18.
 */

public class HistoryInteractorImpl implements HistoryInteractor {

    RestService restService;
    UserManager userManager;

    public HistoryInteractorImpl(RestService restService, UserManager userManager) {
        this.restService = restService;
        this.userManager = userManager;
    }

    @Override
    public Single<HistoryRideResponse> fetchHistory() {
        return restService.historyRideList(new HistoryRideRequest(userManager.getUserId()))
                .map(HistoryParser::parse);
    }
}
