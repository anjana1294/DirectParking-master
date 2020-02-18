package com.directparking.app.ui.aboutus;

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


public class AboutUsFragment extends BaseFragment {

    Unbinder unbinder;
    View rootView;

    @BindView(R.id.tv_about_us_content)
    TextView tvAboutUs;

    public AboutUsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        unbinder = ButterKnife.bind(this,rootView);
        tvAboutUs.setText(new SpannableString(tvAboutUs.getText()));
//        TextJustification.justify(tvAboutUs);
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
