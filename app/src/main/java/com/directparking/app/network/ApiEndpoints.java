package com.directparking.app.network;

public enum ApiEndpoints {

    MOCK("Mock", "http://www.apiary-mock.com/"),

    //RELEASE("Release", "http://profile.appsimity.com/");
    RELEASE("Release", "http://167.71.169.83/");

    public final String name;
    public final String url;

    ApiEndpoints(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public static ApiEndpoints from(String endpoint) {
        for (ApiEndpoints value : values()) {
            if (value.url != null && value.url.equals(endpoint)) {
                return value;
            }
        }
        return MOCK;
    }

    public static boolean isReleaseMode(String endpoint) {
        return from(endpoint) == RELEASE;
    }
}