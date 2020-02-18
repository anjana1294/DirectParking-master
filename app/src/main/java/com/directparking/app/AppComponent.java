package com.directparking.app;

import com.directparking.app.data.prefs.PrefsModule;
import com.directparking.app.data.user.UserModule;
import com.directparking.app.fcm.FcmMessageReceiverService;
import com.directparking.app.fcm.FetchFcmTokenService;
import com.directparking.app.location.LocationModule;
import com.directparking.app.media.MediaModule;
import com.directparking.app.network.NetworkModule;
import com.directparking.app.ui.accept.di.AcceptModule;
import com.directparking.app.ui.chat.chatlist.di.ChatComponent;
import com.directparking.app.ui.chat.chatlist.di.ChatModule;
import com.directparking.app.ui.chat.messages.di.MessageComponent;
import com.directparking.app.ui.chat.messages.di.MessageModule;
import com.directparking.app.ui.confrimation.di.ConfirmationComponent;
import com.directparking.app.ui.confrimation.di.ConfirmationModule;
import com.directparking.app.ui.history.di.HistoryModule;
import com.directparking.app.ui.home.di.HomeComponent;
import com.directparking.app.ui.login.di.LoginComponent;
import com.directparking.app.ui.login.di.LoginModule;
import com.directparking.app.ui.logout.di.LogoutModule;
import com.directparking.app.ui.notification.di.NotificationModule;
import com.directparking.app.ui.parkingslots.di.ParkingSlotModule;
import com.directparking.app.ui.profile.di.ProfileModule;
import com.directparking.app.ui.review.post.di.ReviewComponent;
import com.directparking.app.ui.review.post.di.ReviewModule;
import com.directparking.app.ui.review.view.di.ViewReviewComponent;
import com.directparking.app.ui.review.view.di.ViewReviewModule;
import com.directparking.app.ui.signup.di.SignupComponent;
import com.directparking.app.ui.signup.di.SignupModule;
import com.directparking.app.ui.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, PrefsModule.class, MediaModule.class, UserModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(BaseApplication application);

        Builder appModule(AppModule appModule);

        Builder networkModule(NetworkModule networkModule);

        Builder prefsModule(PrefsModule prefsModule);

        Builder mediaModule(MediaModule mediaModule);

        Builder userModule(UserModule userModule);

        AppComponent build();
    }

    void inject(FcmMessageReceiverService service);

    void inject(FetchFcmTokenService service);

    void inject(SplashActivity activity);

    LoginComponent plus(LoginModule loginModule);

    SignupComponent plus(SignupModule signupModule);

    ConfirmationComponent plus(ConfirmationModule confirmationModule);

    ReviewComponent plus(ReviewModule reviewModule);

    ViewReviewComponent plus(ViewReviewModule viewReviewModule);

    ChatComponent plus(ChatModule chatModule);

    MessageComponent plus(MessageModule messageModule);

    HomeComponent plus(ProfileModule profileModule,
                       LocationModule locationModule,
                       NotificationModule notificationModule,
                       AcceptModule acceptModule,
                       HistoryModule historyModule,
                       ParkingSlotModule parkingSlotModule,
                       LogoutModule logoutModule);
}