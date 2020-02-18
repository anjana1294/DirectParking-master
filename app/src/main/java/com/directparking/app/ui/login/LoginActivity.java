package com.directparking.app.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.PopupMenu;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.directparking.app.BaseApplication;
import com.directparking.app.BuildConfig;
import com.directparking.app.R;
import com.directparking.app.data.prefs.StringPreference;
import com.directparking.app.network.ApiEndpoints;
import com.directparking.app.ui.base.BaseActivity;
import com.directparking.app.ui.home.HomeActivity;
import com.directparking.app.ui.password.forgot.ForgotPasswordActivity;
import com.directparking.app.ui.signup.SignupActivity;
import com.directparking.app.util.AlertUtil;
import com.directparking.app.util.validation.Email;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import timber.log.Timber;

import static com.mobsandgeeks.saripaar.annotation.Password.Scheme.ALPHA_NUMERIC;

public class  LoginActivity extends BaseActivity implements LoginView, ValidationListener {

    private Validator validator;

    @Inject
    LoginPresenter presenter;

    @Inject
    @Named("baseUrl")
    StringPreference apiEndpoint;

    @BindView(R.id.layout_main)
    LinearLayout layoutMain;

    @Order(1)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_email_msg)
    @Email(sequence = 2, messageResId = R.string.invalid_email_msg)
    @BindView(R.id.et_username)
    TextInputEditText etUsername;

    @Order(2)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_password_msg)
    @Password(sequence = 2, messageResId = R.string.invalid_password_msg)
    @BindView(R.id.et_password)
    TextInputEditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @OnClick(R.id.btn_login)
    void login() {
        validator.validate();
    }

    @OnClick(R.id.tv_forgot_password)
    void forgotPassword() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }


    @OnClick(R.id.tv_signup)
    void goToSignup() {
//        showDialog();
        startActivity(new Intent(this, SignupActivity.class));
    }

   /* @BindView(R.id.debug_fab)
    FloatingActionButton debugFab;

    @OnLongClick(R.id.logo)
    boolean toggleDebugFab() {
        if (BuildConfig.DEBUG) {
            if (debugFab.getVisibility() == View.VISIBLE) {
                debugFab.hide();
            } else {
                debugFab.show();
            }
        }
        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ((BaseApplication) getApplication()).getLoginComponent().inject(this);

        etPassword.setTypeface(Typeface.DEFAULT);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.setValidationListener(this);

        presenter.setView(this);

        /*if (BuildConfig.DEBUG) {
            debugFab.setOnClickListener(view -> {
                PopupMenu popup = new PopupMenu(layoutMain.getContext(), view);
                popup.inflate(R.menu.menu_api_endpoint);

                if (ApiEndpoints.isReleaseMode(apiEndpoint.get())) {
                    popup.getMenu().findItem(R.id.menu_release).setChecked(true);
                }

                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_mock:
                            setEndpointAndRelaunch(ApiEndpoints.MOCK.url);
                            return true;
                        case R.id.menu_release:
                            setEndpointAndRelaunch(ApiEndpoints.RELEASE.url);
                            return true;
                        default:
                            return false;
                    }
                });
                popup.show();
            });
        }*/
    }

    private void setEndpointAndRelaunch(String endpoint) {
        Timber.d("Setting network endpoint to %s", endpoint);

        apiEndpoint.set(endpoint);

        ProcessPhoenix.triggerRebirth(this);
    }

    @Override
    public void onValidationSucceeded() {
        presenter.callLogin(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String[] listMessage = error.getCollatedErrorMessage(this).split("\n");
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
        btnLogin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHomeScreen(String msg) {
        AlertUtil.showToast(this, msg);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(layoutMain, msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.destroy();
        ((BaseApplication) getApplication()).releaseLoginComponent();
    }

}