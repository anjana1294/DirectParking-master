package com.directparking.app.ui.accept;

import com.directparking.app.ui.accept.model.AcceptItem;
import com.directparking.app.ui.base.BaseView;

import java.util.List;

interface AcceptView extends BaseView {

    void showList(List<AcceptItem> notifications);

    void onItemClicked(AcceptItem item);

    void clearRidesRequest();

    void onStatusChanged(String response, String status, int position);

}