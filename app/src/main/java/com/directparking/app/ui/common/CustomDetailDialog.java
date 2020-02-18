package com.directparking.app.ui.common;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directparking.app.R;
import com.directparking.app.ui.base.BaseDialog;
import com.directparking.app.ui.custom.LinkifyMovement;
import com.directparking.app.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.directparking.app.util.Constants.DESC;
import static com.directparking.app.util.Constants.TITLE;

public class CustomDetailDialog extends BaseDialog implements LinkifyMovement.OnLinkClickListener {

    private Unbinder unbinder;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @OnClick(R.id.btn_close)
    public void close() {
        super.dismiss();
    }

    public static CustomDetailDialog newInstance(String title, SpannableStringBuilder desc) {
        CustomDetailDialog dialog = new CustomDetailDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putCharSequence(DESC, desc);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_custom_detail, container, false);
        unbinder = ButterKnife.bind(this, dialogView);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvTitle.setText(getArguments().getString(TITLE, ""));
        tvDesc.setText(getArguments().getCharSequence(DESC, ""));
        LinkifyMovement.linkify(Linkify.ALL, tvDesc).setOnLinkClickListener(this);

        return dialogView;
    }

    @Override
    public boolean onClick(TextView textView, String url) {
        return Util.handleUrl(textView.getContext(), url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}