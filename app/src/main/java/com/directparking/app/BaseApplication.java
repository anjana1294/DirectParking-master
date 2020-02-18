package com.directparking.app;

import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.R;
import android.support.multidex.BuildConfig;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.directparking.app.data.event.NetworkEventBusMessage;
import com.directparking.app.data.prefs.PrefsModule;
import com.directparking.app.data.user.UserModule;
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
import com.directparking.app.util.CrashlyticsTree;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.gms.ads.MobileAds;


import net.danlew.android.joda.JodaTimeAndroid;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.directparking.app.util.Constants.EVENT_CONNECTIVITY_CONNECTED;
import static com.directparking.app.util.Constants.EVENT_CONNECTIVITY_LOST;

public class BaseApplication extends MultiDexApplication {

    private Disposable disposable;
    private AppComponent appComponent;
    private LoginComponent loginComponent;
    private SignupComponent signupComponent;
    private HomeComponent homeComponent;

    private ConfirmationComponent confirmationComponent;
    private ReviewComponent reviewComponent;
    private ViewReviewComponent viewReviewComponent;
    private ChatComponent chatComponent;
    private MessageComponent messageComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        StrictMode.enableDefaults();
        appComponent = createAppComponent();
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        ButterKnife.setDebug(BuildConfig.DEBUG);
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected void log(int priority, String tag, @NonNull String msg, Throwable t) {
                super.log(priority, "parking_logs_" + tag, msg, t);
            }
        });
    /*    if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected void log(int priority, String tag, @NonNull String msg, Throwable t) {
                    super.log(priority, "parking_logs_" + tag, msg, t);
                }
            });
        } else {
            Fabric.with(this, new Crashlytics());

            Timber.plant(new CrashlyticsTree());
        }*/

        if (disposable == null) {
            disposable = ReactiveNetwork.observeNetworkConnectivity(this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(connectivity -> {
                        if (connectivity.getState() != NetworkInfo.State.CONNECTED) {
                            EventBus.getDefault().post(new NetworkEventBusMessage(EVENT_CONNECTIVITY_LOST));
                        } else {
                            EventBus.getDefault().post(new NetworkEventBusMessage(EVENT_CONNECTIVITY_CONNECTED));
                        }
                    });
        }

       JodaTimeAndroid.init(this);
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule())
                .networkModule(new NetworkModule())
                .prefsModule(new PrefsModule())
                .mediaModule(new MediaModule())
                .userModule(new UserModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private LoginComponent createLoginComponent() {
        loginComponent = appComponent.plus(new LoginModule());
        return loginComponent;
    }

    public LoginComponent getLoginComponent() {
        if (loginComponent != null) {
            return loginComponent;
        }
        return createLoginComponent();
    }

    public void releaseLoginComponent() {
        loginComponent = null;
    }

    private SignupComponent createSignupComponent() {
        signupComponent = appComponent.plus(new SignupModule());
        return signupComponent;
    }

    public SignupComponent getSignupComponent() {
        if (signupComponent != null) {
            return signupComponent;
        }
        return createSignupComponent();
    }

    public void releaseSignupComponent() {
        signupComponent = null;
    }

    private HomeComponent createHomeComponent() {
        homeComponent = appComponent.plus(
                new ProfileModule(),
                new LocationModule(),
                new NotificationModule(),
                new AcceptModule(),
                new HistoryModule(),
                new ParkingSlotModule(),
                new LogoutModule());
        return homeComponent;
    }

    public HomeComponent getHomeComponent() {
        if (homeComponent != null) {
            return homeComponent;
        }
        return createHomeComponent();
    }

    public void releaseHomeComponent() {
        homeComponent = null;
    }


    private ConfirmationComponent createConfirmationComponent() {
        confirmationComponent = appComponent.plus(new ConfirmationModule());
        return confirmationComponent;
    }

    public ConfirmationComponent getConfirmationComponent() {
        if (confirmationComponent != null) {
            return confirmationComponent;
        }
        return createConfirmationComponent();
    }

    public void releaseConfirmationComponent() {
        confirmationComponent = null;
    }


    private ReviewComponent createReviewComponent() {
        reviewComponent = appComponent.plus(new ReviewModule());
        return reviewComponent;
    }

    public ReviewComponent getReviewComponent() {
        if (reviewComponent != null) {
            return reviewComponent;
        }
        return createReviewComponent();
    }

    public void releaseReviewComponent() {
        reviewComponent = null;
    }

    private ViewReviewComponent createViewReviewComponent() {
        viewReviewComponent = appComponent.plus(new ViewReviewModule());
        return viewReviewComponent;
    }

    public ViewReviewComponent getViewReviewComponent() {
        if (viewReviewComponent != null) {
            return viewReviewComponent;
        }
        return createViewReviewComponent();
    }

    public void releaseViewReviewComponent() {
        viewReviewComponent = null;
    }

    private ChatComponent createChatComponent() {
        chatComponent = appComponent.plus(new ChatModule());
        return chatComponent;
    }

    public ChatComponent getChatComponent() {
        if (chatComponent != null) {
            return chatComponent;
        }
        return createChatComponent();
    }

    public void releaseChatComponent() {
        chatComponent = null;
    }


    private MessageComponent createMessageComponent() {
        messageComponent = appComponent.plus(new MessageModule());
        return messageComponent;
    }

    public MessageComponent getMessageComponent() {
        if (messageComponent != null) {
            return messageComponent;
        }
        return createMessageComponent();
    }

    public void releaseMessageComponent() {
        messageComponent = null;
    }
}