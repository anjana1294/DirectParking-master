package com.directparking.app.ui.signup.di;

import com.directparking.app.ui.signup.SignupActivity;

import dagger.Subcomponent;

@SignupScope
@Subcomponent(modules = {SignupModule.class})
public interface SignupComponent {

    void inject(SignupActivity activity);

}