package com.directparking.app.ui.confrimation;

import com.directparking.app.ui.base.BaseView;
import com.directparking.app.ui.confrimation.model.CancelResponse;
import com.directparking.app.ui.confrimation.model.ConfirmationResponse;

/**
 * Created by root on 24/7/18.
 */

public interface ConfirmationView extends BaseView {
    void showSuccessAlert(ConfirmationResponse msg);
    void showCancelAlert(CancelResponse msg);
}

