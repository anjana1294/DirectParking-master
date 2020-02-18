package com.directparking.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.directparking.app.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class Util {

    private Util() {
    }

    public static void showSoftKeyboard(final Context context, final EditText editText) {
        editText.requestFocus();
        new Handler().postDelayed(() -> {
            final InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 0);
    }

    public static void hideSoftKeyboard(final Context context, final EditText editText) {
        editText.clearFocus();
        final InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static boolean isGooglePlayServicesAvailable(Activity activity, int requestCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity.getApplication().getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, requestCode)
                        .show();
            } else {
                Timber.i("Google play services are not available!");
                AlertUtil.showToast(activity.getApplication().getApplicationContext(), activity.getString(R.string.fcm_not_supported_msg));
                activity.finish();
            }
            return false;
        }
        return true;
    }

    public static String joinWith(CharSequence delimiter, final String... object) {
        StringBuilder sb = new StringBuilder();

        final List<String> objects = Arrays.asList(object);

        for (int i = 0, j = objects.size(); i < j; i++) {
            if (!TextUtils.isEmpty(objects.get(i))) {
                sb.append(objects.get(i).trim());
                if (i < (j - 1)) {
                    sb.append(delimiter);
                }
            }
        }
        return sb.toString();
    }

    public static void callPhoneNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }


    public static void showLocationOnGoogleMaps(Context context, LatLng location) {
        String data = String.format(Locale.getDefault(), "geo:0,0?q=%f,%f", location.latitude, location.longitude);
        openGoogleMaps(context, data);
    }

    public static void showLocationOnGoogleMapsWithLabel(Context context, LatLng location, String label) {
        String data = String.format(Locale.getDefault(), "geo:0,0?q=%f,%f(%s)", location.latitude, location.longitude, label);
        openGoogleMaps(context, data);
    }

    private static void openGoogleMaps(Context context, String data) {
        Uri mapUri = Uri.parse(data);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        }
    }

    public static int convertDpToPixels(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static boolean handleUrl(Context context, String url) {
        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        } else if (url.startsWith("mailto:")) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(url));
            intent.putExtra(Intent.EXTRA_EMAIL, Uri.parse(url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        } else if (url.startsWith("http")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        }
        return false;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (1.0913712 * Float.valueOf(String.valueOf(dist)));
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}