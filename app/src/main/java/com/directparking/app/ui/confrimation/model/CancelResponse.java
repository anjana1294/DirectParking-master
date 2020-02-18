package com.directparking.app.ui.confrimation.model;

/**
 * Created by Mohit on 17/8/18.
 */

public class CancelResponse {
    private String success;
    private boolean status;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;
}
