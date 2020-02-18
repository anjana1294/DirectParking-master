package com.directparking.app.ui.profile.edit;

import android.text.TextUtils;

import com.directparking.app.data.user.UserManager;
import com.directparking.app.network.RestService;
import com.directparking.app.ui.login.model.CarData;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.profile.edit.model.UpdateProfileRequest;
import com.directparking.app.ui.profile.view.MyProfileParser;
import com.directparking.app.ui.profile.view.model.MyProfileRequest;
import com.directparking.app.ui.profile.view.model.MyProfileResponse;
import com.directparking.app.util.FileCacher;
import com.directparking.app.util.FileUtil;
import com.google.gson.Gson;

import java.io.File;

import io.reactivex.Single;

import static com.directparking.app.util.Constants.PROFILE_JPG;

public class ProfileInteractorImpl implements ProfileInteractor {

    private RestService restService;
    private UserManager userManager;
    private FileCacher<File> fileCacher;

    public ProfileInteractorImpl(RestService restService, UserManager userManager, FileCacher<File> fileCacher) {
        this.restService = restService;
        this.userManager = userManager;
        this.fileCacher = fileCacher;
    }

    @Override
    public Single<String> updateProfile(UserData userData, CarData carData) {

        UserData newUserData = new UserData.Builder()
                .setUserId(userManager.getUserId())
                .setFirstName(userData.getFirstName())
                .setLastName(userData.getLastName())
                .setDob(userData.getDob())
                .setProfile(!TextUtils.isEmpty(userData.getProfile()) ? FileUtil.convertFileToBase64EncodedString(userData.getProfile()) : null)
                .build();


        CarData newCarData = new CarData.Builder()
                .setCarMake(carData.getCarMake())
                .setModel(carData.getModel())
                .setColor(carData.getColor())
                .setPlateNumber(carData.getPlateNumber())
                .setLicenceNumber(carData.getLicenceNumber())
                .build();

        UpdateProfileRequest request = new UpdateProfileRequest.Builder()
                .setFcmToken(userManager.getFcmToken())
                .setUserDetails(newUserData)
                .setCarDetails(newCarData)
                .build();


        return restService.updateProfile(request)
                .map(response -> {
                    String msg = ProfileParser.parse(response);

                    String storedUserData = userManager.getUserData();
                    String storedCarData = userManager.getCarData();

                    UserData updatedUserData = new Gson().fromJson(storedUserData, UserData.class);
                    CarData updatedCarDetail = new Gson().fromJson(storedCarData, CarData.class);

                    updatedUserData.setFirstName(userData.getFirstName());
                    updatedUserData.setLastName(userData.getLastName());

                    updatedCarDetail.setCarMake(carData.getCarMake());
                    updatedCarDetail.setModel(carData.getModel());
                    updatedCarDetail.setColor(carData.getColor());
                    updatedCarDetail.setPlateNumber(carData.getPlateNumber());
                    updatedCarDetail.setLicenceNumber(carData.getLicenceNumber());


                    if (!TextUtils.isEmpty(userData.getProfile())) {
                        FileUtil.storeFileInCache(PROFILE_JPG, userData.getProfile(), userManager, fileCacher);
                    }

                    userManager.updateUserData(new Gson().toJson(updatedUserData));
                    userManager.updateCarData(new Gson().toJson(updatedCarDetail));

                    return msg;
                });
    }

    @Override
    public Single<MyProfileResponse> getProfile() {
        MyProfileRequest myProfileRequest = new MyProfileRequest();
        myProfileRequest.setUserId(userManager.getUserId());
        return restService.updateProfile(myProfileRequest)
                .map(MyProfileParser::parse);

    }
}