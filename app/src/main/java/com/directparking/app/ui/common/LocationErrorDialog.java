package com.directparking.app.ui.common;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.directparking.app.ui.base.BaseDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class LocationErrorDialog extends BaseDialog {

    private Callback callback;

    public static LocationErrorDialog newInstance() {
        return new LocationErrorDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity())
                .setTitle("Location error")
                .setMessage("Sorry device location couldn't be accessed, you can't proceed further!")
                .setPositiveButton("Retry", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnShowListener(dialog -> {
            Button positiveBtn = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
            positiveBtn.setOnClickListener(view ->
                    new RxPermissions(getActivity())
                            .request(Manifest.permission.ACCESS_FINE_LOCATION)
                            .subscribe(granted -> {
                                if (granted) {
                                    dismiss();

                                    if (callback != null) {
                                        callback.requestLocation();
                                    }
                                }
                            }));
        });

        return alertDialog;
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    public interface Callback {
        void requestLocation();
    }
}