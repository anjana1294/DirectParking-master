package com.directparking.app.ui.signup;

import android.support.annotation.NonNull;

import com.directparking.app.ui.signup.model.IdNameItem;
import com.directparking.app.ui.signup.model.UniversityResponse;

import java.util.List;

import retrofit2.Response;

public class UniversityParser {

    @NonNull
    public static List<IdNameItem> parse(Response<UniversityResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            UniversityResponse body = response.body();

            if (body != null) {
                if (body.isStatus()) {
                    if (body.getData() != null && !body.getData().isEmpty()) {
                        List<IdNameItem> universities = body.getData();
                        universities.add(0, new IdNameItem("0", "Select University"));
                        return universities;
                    } else {
                        throw new RuntimeException("List is empty!");
                    }
                } else {
                    throw new RuntimeException(body.getMsg());
                }
            } else {
                throw new RuntimeException("Response payload is empty!");
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}