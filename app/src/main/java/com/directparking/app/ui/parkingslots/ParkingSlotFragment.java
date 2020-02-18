package com.directparking.app.ui.parkingslots;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directparking.app.R;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.custom.TextJustification;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ParkingSlotFragment extends BaseFragment {

    View rootView;
    Unbinder unbinder;

    @BindView(R.id.tv_parking_lots_content)
    TextView tvParkingLotsContent;

    public static ParkingSlotFragment newInstance() {
        ParkingSlotFragment fragment = new ParkingSlotFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_parking_slot, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }

}
