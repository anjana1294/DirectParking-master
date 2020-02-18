package com.directparking.app.ui.history;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.history.adapter.HistoryPagerAdapter;
import com.directparking.app.ui.history.model.HistoryRideResponse;
import com.directparking.app.util.AlertUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BaseFragment implements HistoryView {

    @Inject
    HistoryPresenter presenter;

    @BindView(R.id.tab_layout_history)
    TabLayout tabLayoutHistory;

    @BindView(R.id.history_pager)
    ViewPager vpHistory;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    Unbinder unbinder;
    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication) getActivity().getApplication()).getHomeComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_history, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        getHistoryData();
        return rootView;
    }

    private void getHistoryData() {
        presenter.setView(this);
        presenter.fetchHistory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        unbinder.unbind();
    }

    @Override
    public void showHistoryList(HistoryRideResponse rides) {
//        vpHistory.beginFakeDrag();
        vpHistory.setAdapter(new HistoryPagerAdapter(getChildFragmentManager(), rides));
        tabLayoutHistory.setupWithViewPager(vpHistory);
    }

    @Override
    public void showProgressBar() {
        super.showProgressBar();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        super.hideProgressBar();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        super.showMessage(msg);
        AlertUtil.showSnackBar(rootView,msg);
    }
}
