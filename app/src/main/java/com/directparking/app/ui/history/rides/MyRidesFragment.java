package com.directparking.app.ui.history.rides;


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
public class MyRidesFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    EmptyRecyclerView rvMyRides;

    View rootView;
    Unbinder unbinder;
    List<HistoryRide> myRidesList;

    @BindView(R.id.emptyView)
    View emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myRidesList = (List<HistoryRide>) getArguments().getSerializable("myRidesList");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_rides, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        HistoryItemRecyclerAdapter historyItemRecyclerAdapter =
                new HistoryItemRecyclerAdapter(myRidesList, context());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context());
        rvMyRides.setLayoutManager(layoutManager);
        rvMyRides.setHasFixedSize(true);
        rvMyRides.setEmptyView(emptyView);
        rvMyRides.setAdapter(historyItemRecyclerAdapter);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public static Fragment newInstance(List<HistoryRide> myRidesList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("myRidesList", (Serializable) myRidesList);
        MyRidesFragment myRidesFragment = new MyRidesFragment();
        myRidesFragment.setArguments(bundle);
        return myRidesFragment;
    }
}
