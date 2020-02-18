package com.directparking.app.util.validation;

import android.text.TextUtils;
import android.util.Patterns;

import com.mobsandgeeks.saripaar.AnnotationRule;

class EmailRule extends AnnotationRule<Email, String> {

    protected EmailRule(Email email) {
        super(email);
    }

    @Override
    public boolean isValid(final String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }
}