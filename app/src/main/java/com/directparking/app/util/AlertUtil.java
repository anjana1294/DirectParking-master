package com.directparking.app.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.directparking.app.R;

public class AlertUtil {

    private AlertUtil() {
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }

    public static void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackBar(View view, String msg, int duration) {
        Snackbar.make(view, msg, duration).show();
    }

    public static void showIndefiniteSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).show();
    }

    public static void showActionSnackBar(View view, String msg, String action, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE).setAction(action, listener);
        TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
//        textView.setMaxLines(3);
        snackbar.show();
    }

    public static void  showAlertDialog(Context context, String title, String msg, String actionName) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setCancelable(true);
        alert.setPositiveButton(actionName, (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    public static void showActionAlertDialog(Context context, String title, String msg,
                                             String positiveAction, String negativeAction,
                                             DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setCancelable(true);
        alert.setPositiveButton(positiveAction, listener);
        alert.setNegativeButton(negativeAction, (dialog, which) -> dialog.dismiss());
        alert.show();
    }


    public static void showMyActionAlertDialog(Context context, String title, String msg,
                                             String positiveAction,
                                             DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.setPositiveButton(positiveAction, listener);
        alert.show();
    }



}