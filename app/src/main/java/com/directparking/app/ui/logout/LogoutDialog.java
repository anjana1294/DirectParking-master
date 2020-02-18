package com.directparking.app.ui.logout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.ui.base.BaseDialog;
import com.directparking.app.util.AlertUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LogoutDialog extends BaseDialog implements LogoutView {

    private Unbinder unbinder;
    private Callback callback;

    @Inject
    Context context;

    @Inject
    LogoutPresenter presenter;

    @OnClick(R.id.btn_yes)
    void logout() {
        presenter.callLogout();
    }

    @OnClick(R.id.btn_no)
    public void dismiss() {
        super.dismiss();
    }

    public static LogoutDialog newInstance() {
        return new LogoutDialog();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_logout, container, false);
        unbinder = ButterKnife.bind(this, dialogView);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialogView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showToast(context, msg);
    }

    @Override
    public void showLoginScreen() {
        dismiss();

        if (callback != null) {
            callback.showLoginScreen();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        callback = null;
        super.onDetach();
    }

    public interface Callback {
        void showLoginScreen();
    }
}