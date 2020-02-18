package com.directparking.app.data.event;

public class NetworkEventBusMessage {

    private int resultCode;
    private String resultValue;

    public NetworkEventBusMessage(int resultCode) {
        this.resultCode = resultCode;
    }

    public NetworkEventBusMessage(int resultCode, String resultValue) {
        this.resultCode = resultCode;
        this.resultValue = resultValue;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultValue() {
        return resultValue;
    }
}