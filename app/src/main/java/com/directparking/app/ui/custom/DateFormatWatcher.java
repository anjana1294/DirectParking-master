package com.directparking.app.ui.custom;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;


public class DateFormatWatcher implements TextWatcher {

    // Change this to what you want... ' ', '-' etc..
    private static final char delimiter = '/';

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        formatDob(editable, 3, false);
    }

    private void formatDob(Editable editable, int position, boolean isStandard) {
        if (editable.length() > 0) {
            final char c = editable.charAt(editable.length() - 1);
            if (delimiter == c) {
                editable.delete(editable.length() - 1, editable.length());
            } else {
                // Remove spacing char
                if (editable.length() > 0 && (editable.length() % position) == 0) {
                    if (delimiter == c) {
                        editable.delete(editable.length() - 1, editable.length());
                    }
                }

                // Insert char where needed
                if (isStandard) {
                    if (editable.length() > 0 && ((editable.length() % position) == 0 || (editable.length() % position + 3) == 0)) {
                        if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf(delimiter)).length <= 2) {
                            editable.insert(editable.length() - 1, String.valueOf(delimiter));
                        }
                    }
                } else {
                    if (editable.length() > 0 && (editable.length() % position) == 0) {
                        if (Character.isDigit(c) && TextUtils.split(editable.toString(), String.valueOf(delimiter)).length <= 2) {
                            editable.insert(editable.length() - 1, String.valueOf(delimiter));
                        }
                    }
                }
            }
        }
    }
}
