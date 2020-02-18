package com.directparking.app.ui.password.change;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseFragment;
import com.directparking.app.ui.logout.LogoutDialog.Callback;
import com.directparking.app.util.AlertUtil;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangePasswordFragment extends BaseFragment implements
        ChangePasswordView, ValidationListener {

    private Callback callback;
    private Unbinder unbinder;
    private Validator validator;

    @Inject
    ChangePasswordPresenter presenter;

    @Order(1)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_password_msg)
    @BindView(R.id.et_old_password)
    TextInputEditText etOldPassword;

    @Order(2)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_password_msg)
    @Password(sequence = 2, messageResId = R.string.invalid_password_msg)
    @BindView(R.id.et_new_password)
    TextInputEditText etNewPassword;

    @Order(3)
    @NotEmpty(sequence = 1, trim = true, messageResId = R.string.empty_password_msg)
    @ConfirmPassword(sequence = 2, messageResId = R.string.password_mistach_msg)
    @BindView(R.id.et_confirm_password)
    TextInputEditText etConfirmPassword;

    @OnClick(R.id.btn_cancel)
    void cancel() {
        getFragmentManager().popBackStackImmediate();
    }

    @OnClick(R.id.btn_save)
    void save() {
        validator.validate();
    }

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication) getActivity().getApplication()).getHomeComponent().inject(this);

        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.setValidationListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
    }

    @Override
    public void onValidationSucceeded() {
        String oldPwd = etOldPassword.getText().toString();
        String newPwd = etNewPassword.getText().toString();
        presenter.changePassword(etOldPassword.getText().toString().trim(), etNewPassword.getText().toString().trim());

//        if (!newPwd.equalsIgnoreCase(oldPwd)) {
//            AlertUtil.showActionAlertDialog(context(), "Alert",
//                    "Once the password is changed successfully, you will need to login again. Still want to proceed?",
//                    "Yes", "No", (dialog, which) ->
//                            presenter.changePassword(etOldPassword.getText().toString().trim(), etNewPassword.getText().toString().trim()));
//        } else {
//            etNewPassword.setError("New password can't be same as old password!");
//            etNewPassword.requestFocus();
//        }
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
    public void showMessage(String msg) {
        AlertUtil.showToast(context(), msg);
    }

    @Override
    public void showLoginScreen() {
        if (callback != null) {
            callback.showLoginScreen();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        presenter.destroy();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }
}