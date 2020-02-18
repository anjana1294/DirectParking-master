package com.directparking.app.network;

import com.directparking.app.location.model.UpdateLocationRequest;
import com.directparking.app.location.model.UpdateLocationResponse;
import com.directparking.app.ui.accept.model.AcceptListResponse;
import com.directparking.app.ui.accept.model.AcceptRequest;
import com.directparking.app.ui.accept.model.ChangeRideStatusRequest;
import com.directparking.app.ui.accept.model.ChangeRideStatusResponse;
import com.directparking.app.ui.accept.model.ClearAcceptResponse;
import com.directparking.app.ui.chat.chatlist.model.ChatListRequest;
import com.directparking.app.ui.chat.chatlist.model.ChatListResponse;
import com.directparking.app.ui.chat.messages.model.MessageItem;
import com.directparking.app.ui.chat.messages.model.MessageListRequest;
import com.directparking.app.ui.chat.messages.model.MessageListResponse;
import com.directparking.app.ui.chat.messages.model.SendMessageRequest;
import com.directparking.app.ui.chat.messages.model.SendMessageResponse;
import com.directparking.app.ui.confrimation.model.CancelRequest;
import com.directparking.app.ui.confrimation.model.CancelResponse;
import com.directparking.app.ui.confrimation.model.ConfirmationRequest;
import com.directparking.app.ui.confrimation.model.ConfirmationResponse;
import com.directparking.app.ui.history.model.HistoryRideRequest;
import com.directparking.app.ui.history.model.HistoryRideResponse;
import com.directparking.app.ui.home.model.AfterAcceptActionRequest;
import com.directparking.app.ui.home.model.AfterAcceptActionResponse;
import com.directparking.app.ui.home.model.RideDetailRequest;
import com.directparking.app.ui.home.model.RideDetailResponse;
import com.directparking.app.ui.home.model.TrackDriverLocationRequest;
import com.directparking.app.ui.home.model.TrackDriverLocationResponse;
import com.directparking.app.ui.login.model.LoginRequest;
import com.directparking.app.ui.login.model.LoginResponse;
import com.directparking.app.ui.logout.model.LogoutRequest;
import com.directparking.app.ui.logout.model.LogoutResponse;
import com.directparking.app.ui.map.model.DirectionResponse;
import com.directparking.app.ui.notification.model.ClearNotificationResponse;
import com.directparking.app.ui.notification.model.NotificationListResponse;
import com.directparking.app.ui.notification.model.NotificationRequest;
import com.directparking.app.ui.password.change.model.ChangePasswordRequest;
import com.directparking.app.ui.password.change.model.ChangePasswordResponse;
import com.directparking.app.ui.password.forgot.model.ForgotPasswordRequest;
import com.directparking.app.ui.password.forgot.model.ForgotPasswordResponse;
import com.directparking.app.ui.profile.edit.model.UpdateProfileRequest;
import com.directparking.app.ui.profile.edit.model.UpdateProfileResponse;
import com.directparking.app.ui.profile.view.model.MyProfileRequest;
import com.directparking.app.ui.profile.view.model.MyProfileResponse;
import com.directparking.app.ui.review.post.model.ReviewRatingRequest;
import com.directparking.app.ui.review.post.model.ReviewRatingResponse;
import com.directparking.app.ui.review.view.model.ReviewListRequest;
import com.directparking.app.ui.review.view.model.ReviewListResponse;
import com.directparking.app.ui.signup.model.SignupRequest;
import com.directparking.app.ui.signup.model.SignupResponse;
import com.directparking.app.ui.signup.model.UniversityRequest;
import com.directparking.app.ui.signup.model.UniversityResponse;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface RestService {

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/login")
    Single<Response<LoginResponse>> login(@Body LoginRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/forgotPassword")
    Single<Response<ForgotPasswordResponse>> forgotPassword(@Body ForgotPasswordRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/signup")
    Single<Response<SignupResponse>> signup(@Body SignupRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/universityList")
    Single<Response<UniversityResponse>> universityList(@Body UniversityRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/universityList")
    Single<Response<UniversityResponse>> universitySuggestionList(@Body UniversityRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/updateProfile")
    Single<Response<UpdateProfileResponse>> updateProfile(@Body UpdateProfileRequest request);

    @Headers({"content-type: application/json"})
    @GET("https://maps.googleapis.com/maps/api/directions/json?mode=driving")
    Observable<Response<DirectionResponse>> directions(@QueryMap Map<String, String> query);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/getNotificationList")
    Single<Response<NotificationListResponse>> notificationList(@Body NotificationRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/clearNotification")
    Single<Response<ClearNotificationResponse>> clearNotification(@Body NotificationRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/updateLocation")
    Single<Response<UpdateLocationResponse>> updateLocation(@Body UpdateLocationRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/changePassword")
    Single<Response<ChangePasswordResponse>> changePassword(@Body ChangePasswordRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/logout")
    Single<Response<LogoutResponse>> logout(@Body LogoutRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/requestRide")
    Single<Response<ConfirmationResponse>> requestRide(@Body ConfirmationRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/rideList")
    Single<Response<AcceptListResponse>> ridesList(@Body AcceptRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/clearNotification")
    Single<Response<ClearAcceptResponse>> clearRides(@Body AcceptRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/rideStatus")
    Single<Response<ChangeRideStatusResponse>>  rideStatus(@Body ChangeRideStatusRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/userInfo")
    Single<Response<RideDetailResponse>> rideDetails(@Body RideDetailRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/afterAcceptStatus")

    Single<Response<AfterAcceptActionResponse>> afterAcceptStatus(@Body AfterAcceptActionRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/addRating")
    Single<Response<ReviewRatingResponse>> addRating(@Body ReviewRatingRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/rideHistory")
    Single<Response<HistoryRideResponse>> historyRideList(@Body HistoryRideRequest request);


    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/riderRequestCancel")
    Single<Response<CancelResponse>> riderRequestCancel(@Body CancelRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/rideDetail")
    Single<Response<com.directparking.app.ui.review.post.model.RideDetailResponse>> rideDetail(@Body com.directparking.app.ui.review.post.model.RideDetailRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/reviewList")
    Single<Response<ReviewListResponse>> reviewList(@Body ReviewListRequest request);


    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/getChatList")
    Single<Response<ChatListResponse>> chatList(@Body ChatListRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/chatHistory")
    Single<Response<MessageListResponse>> messageList(@Body MessageListRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/sendMsg")
    Single<Response<SendMessageResponse>> sendMesssage(@Body MessageItem request);


    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/trackUserRide")
    Single<Response<TrackDriverLocationResponse>> trackOrder(@Body TrackDriverLocationRequest request);

    @Headers({"content-type: application/json"})
    @POST("/directparking/index.php/Android/getProfile")
    Single<Response<MyProfileResponse>> updateProfile(@Body MyProfileRequest request);

}