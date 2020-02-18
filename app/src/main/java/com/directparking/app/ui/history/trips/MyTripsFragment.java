package com.directparking.app.ui.history.trips;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.directparking.app.R;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.custom.EmptyRecyclerView;
import com.directparking.app.ui.history.adapter.HistoryItemRecyclerAdapter;
import com.directparking.app.ui.history.model.HistoryRide;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTripsFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    EmptyRecyclerView rvMyTrips;

    View rootView;
    Unbinder unbinder;
    List<HistoryRide> myTripsList;

    @BindView(R.id.emptyView)
    View emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myTripsList = (List<HistoryRide>) getArguments().getSerializable("myTripList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_trips, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        HistoryItemRecyclerAdapter historyItemRecyclerAdapter =
                new HistoryItemRecyclerAdapter(myTripsList, context());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context());
        rvMyTrips.setLayoutManager(layoutManager);
        rvMyTrips.setHasFixedSize(true);
        rvMyTrips.setEmptyView(emptyView);
        rvMyTrips.setAdapter(historyItemRecyclerAdapter);
        return rootView;
    }

    public static Fragment newInstance(List<HistoryRide> myTripsList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("myTripList", (Serializable) myTripsList);
        MyTripsFragment myTripsFragment = new MyTripsFragment();
        myTripsFragment.setArguments(bundle);
        return myTripsFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
