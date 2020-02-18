package com.directparking.app.ui.accept;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;
import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.accept.adapter.RidesListRecyclerAdapter;
import com.directparking.app.ui.accept.model.AcceptItem;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.common.SimpleDetailDialog;
import com.directparking.app.ui.custom.EmptyRecyclerView;
import com.directparking.app.util.AlertUtil;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.directparking.app.util.Constants.ACCEPT_STATUS;
import static com.directparking.app.util.Constants.CANCEL_STATUS;
import static com.directparking.app.util.Constants.DECLINE_STATUS;
import static com.directparking.app.util.Constants.SIMPLE_DETAIL_DIALOG;

public class AcceptFragment extends BaseFragment implements AcceptView, StatusListener {

    private Unbinder unbinder;
    List<AcceptItem> newRidesList;
    View rootView;

    RidesListRecyclerAdapter ridesListRecyclerAdapter;
    @Inject
    AcceptPresenter presenter;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView rvRides;

    @BindView(R.id.fab_clear)
    FloatingActionButton fabClear;
    private Callback callback;




    @Override
    @OnClick(R.id.fab_clear)
    public void clearRidesRequest() {
        AlertUtil.showActionAlertDialog(context(),"Clear", "Are you sure you want to clear all rides?",
                "Yes", "No", (dialog, which) -> presenter.clearRides());
    }


    public static AcceptFragment newInstance() {
        return new AcceptFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getActivity().getApplication()).getHomeComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_accept, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context());
        rvRides.setLayoutManager(layoutManager);
        rvRides.setHasFixedSize(true);
        rvRides.setEmptyView(emptyView);
        swipeLayout.setOnRefreshListener(() -> presenter.fetchRides());
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.setView(this);
        presenter.fetchRides();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void showEmptyView() {

        emptyView.setVisibility(View.VISIBLE);
        rvRides.setVisibility(View.GONE);
        fabClear.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        swipeLayout.setRefreshing(true);
        emptyView.setVisibility(View.GONE);
        rvRides.setVisibility(View.GONE);
        fabClear.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(swipeLayout, msg);
    }

    @Override
    public void showList(List<AcceptItem> newRides) {
        this.newRidesList = newRides;
        ridesListRecyclerAdapter = new RidesListRecyclerAdapter(getContext(), newRides, this);
        rvRides.setAdapter(ridesListRecyclerAdapter);
        emptyView.setVisibility(View.GONE);
        rvRides.setVisibility(View.VISIBLE);
        fabClear.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(AcceptItem item) {
        SimpleDetailDialog.newInstance(item.getDrop_address(), item.getPick_address())
                .show(getChildFragmentManager(), SIMPLE_DETAIL_DIALOG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroy();
        unbinder.unbind();
    }

    @Override
    public void onStatusChange(int position, String status) {
        String msg = "Do you want to accept this ride request?";

        if (status == CANCEL_STATUS) {
            msg = "Are you sure you want to decline this ride request?";
        }
        AlertUtil.showActionAlertDialog(context(), "Confirmation", msg,
                "Yes"
                , "No", (dialog, which) -> presenter.changeRideStatus(newRidesList.get(position).getUser_id(),
                      newRidesList.get(position).getId(), status, position)
        );

    }

    @Override
    public void onStatusChanged(String msg, String status, int position) {
        if (status == ACCEPT_STATUS) {
            status = "Accepted";
        } else if (status == CANCEL_STATUS) {
            status = "Cancelled";
        } else if (status == DECLINE_STATUS) {
            status = "Cancelled";
        }
        newRidesList.get(position).setStatus(status);
        ridesListRecyclerAdapter.notifyDataSetChanged();
        AlertUtil.showSnackBar(rootView, msg);


        final Handler handler = new Handler();
        String finalStatus = status;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callback != null && finalStatus == "Accepted") {
                    callback.openHomeScreen();
                }
            }
        }, 2000);


    }



    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    public interface Callback {
        void openHomeScreen();
    }
}