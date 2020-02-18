package com.directparking.app.ui.confrimation.di;

import com.directparking.app.ui.confrimation.ConfirmationActivity;

import dagger.Subcomponent;

/**
 * Created by root on 24/7/18.
 */

@ConfirmationScope
@Subcomponent(modules = {ConfirmationModule.class})
public interface ConfirmationComponent {
    void inject(ConfirmationActivity activity);
}
