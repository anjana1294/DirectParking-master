package com.directparking.app.ui.common;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.directparking.app.ui.base.BaseDialog;

import static com.directparking.app.util.Constants.DESC;
import static com.directparking.app.util.Constants.TITLE;

public class SimpleDetailDialog extends BaseDialog {

    public static SimpleDetailDialog newInstance(String title, String desc) {
        SimpleDetailDialog dialog = new SimpleDetailDialog();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(DESC, desc);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString(TITLE, ""))
                .setMessage(getArguments().getString(DESC, ""))
                .setPositiveButton("Close", null)
                .create();
    }
}