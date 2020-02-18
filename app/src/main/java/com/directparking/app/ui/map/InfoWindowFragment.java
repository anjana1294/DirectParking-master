package com.directparking.app.ui.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directparking.app.R;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.util.Util;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class InfoWindowFragment extends BaseFragment {

    private static final String INFO_TITLE = "infoTitle";
    private static final String INFO_SNIPPET = "infoSnippet";
    private static final String INFO_POSITION = "infoPosition";

    private Unbinder unbinder;

    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @OnClick(R.id.iv_call)
    void makeCall() {
        Util.callPhoneNumber(context(), getArguments().getString(INFO_TITLE));
    }

    @OnClick(R.id.iv_map)
    void showMap() {
        Util.showLocationOnGoogleMaps(context(), getArguments().getParcelable(INFO_POSITION));
    }

    public static InfoWindowFragment newInstance(String title, String snippet, LatLng position) {
        InfoWindowFragment fragment = new InfoWindowFragment();
        Bundle args = new Bundle();
        args.putString(INFO_TITLE, title);
        args.putString(INFO_SNIPPET, snippet);
        args.putParcelable(INFO_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.fragment_info_window, container, false);
        unbinder = ButterKnife.bind(this, dialogView);

        tvPhone.setText(getArguments().getString(INFO_TITLE));
        tvAddress.setText(getArguments().getString(INFO_SNIPPET));

        return dialogView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}