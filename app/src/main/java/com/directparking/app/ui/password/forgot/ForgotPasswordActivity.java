package com.directparking.app.ui.password.forgot;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.util.AlertUtil;
import com.directparking.app.util.validation.Email;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordView, ValidationListener {

    private Validator validator;

    @Inject
    ForgotPasswordPresenter presenter;

    @BindView(R.id.layout_main)
    LinearLayout layoutMain;

    @Order(1)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_email_msg)
    @Email(sequence = 2, messageResId = R.string.invalid_email_msg)
    @BindView(R.id.et_username)
    TextInputEditText etUsername;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btn_send)
    Button btnSend;

    @OnClick(R.id.btn_send)
    void submit() {
        validator.validate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        ((BaseApplication) getApplication()).getLoginComponent().inject(this);

        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.setValidationListener(this);
        validator.registerAnnotation(Email.class);

        presenter.setView(this);
    }

    @Override
    public void onValidationSucceeded() {
        presenter.callForgotPassword(etUsername.getText().toString().trim());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String[] listMessage = error.getCollatedErrorMessage(context()).split("\n");
            String msg = listMessage[0];

            if (view instanceof EditText) {
                ((EditText) view).setError(msg);
                view.requestFocus();
            } else {
                showMessage(msg);
            }
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        btnSend.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        btnSend.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(layoutMain, msg);
    }

    @Override
    public void showLoginScreen(String msg) {
        AlertUtil.showToast(context(), msg);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
    }
}