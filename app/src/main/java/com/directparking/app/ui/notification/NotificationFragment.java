package com.directparking.app.ui.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.common.SimpleDetailDialog;
import com.directparking.app.ui.custom.EmptyRecyclerView;
import com.directparking.app.ui.notification.model.NotificationItem;
import com.directparking.app.util.AlertUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.directparking.app.util.Constants.SIMPLE_DETAIL_DIALOG;

public class NotificationFragment extends BaseFragment implements NotificationView {

    private Unbinder unbinder;
    private NotificationController controller;
    private List<NotificationItem> notifications = new ArrayList<>();

    @Inject
    NotificationPresenter presenter;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView rvNotifications;

    @BindView(R.id.fab_clear)
    FloatingActionButton fabClear;

    @Override
    @OnClick(R.id.fab_clear)
    public void clearNotifications() {
        AlertUtil.showActionAlertDialog(context(), "Clear", "Are you sure you want to clear all notifications?",
                "Yes", "No", (dialog, which) -> presenter.clearNotifications());
    }

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication) getActivity().getApplication()).getHomeComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context());
        rvNotifications.setLayoutManager(layoutManager);
        rvNotifications.addItemDecoration(new DividerItemDecoration(context(), DividerItemDecoration.VERTICAL));
        rvNotifications.setHasFixedSize(true);
        rvNotifications.setEmptyView(emptyView);

        controller = new NotificationController(this);
        rvNotifications.setAdapter(controller.getAdapter());

        swipeLayout.setOnRefreshListener(() -> presenter.fetchNotifications());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
        presenter.fetchNotifications();
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        rvNotifications.setVisibility(View.GONE);
        fabClear.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        swipeLayout.setRefreshing(true);
        emptyView.setVisibility(View.GONE);
        rvNotifications.setVisibility(View.GONE);
        fabClear.setVisibility(View.GONE);
        notifications.clear();
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
    public void showList(List<NotificationItem> newNotifications) {
        notifications.addAll(newNotifications);
        controller.setList(notifications);
        emptyView.setVisibility(View.GONE);
        rvNotifications.setVisibility(View.VISIBLE);
        fabClear.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(NotificationItem item) {
        SimpleDetailDialog.newInstance(item.getTitle(), item.getDescription())
                .show(getChildFragmentManager(), SIMPLE_DETAIL_DIALOG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.destroy();
        unbinder.unbind();
    }
}