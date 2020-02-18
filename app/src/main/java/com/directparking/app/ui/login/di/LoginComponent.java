package com.directparking.app.ui.login.di;

import com.directparking.app.ui.login.LoginActivity;
import com.directparking.app.ui.password.forgot.ForgotPasswordActivity;

import dagger.Subcomponent;

@LoginScope
@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {

    void inject(LoginActivity activity);

    void inject(ForgotPasswordActivity activity);

}