package net.directparking;

import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.multidex.BuildConfig;
import android.support.multidex.MultiDexApplication;

import net.directparking.data.event.NetworkEventBusMessage;
import net.directparking.data.prefs.PrefsModule;
import net.directparking.data.user.UserModule;
import net.directparking.location.LocationModule;
import net.directparking.media.MediaModule;
import net.directparking.network.NetworkModule;
import net.directparking.ui.accept.di.AcceptModule;
import net.directparking.ui.chat.chatlist.di.ChatComponent;
import net.directparking.ui.chat.chatlist.di.ChatModule;
import net.directparking.ui.chat.messages.di.MessageComponent;
import net.directparking.ui.chat.messages.di.MessageModule;
import net.directparking.ui.confrimation.di.ConfirmationComponent;
import net.directparking.ui.confrimation.di.ConfirmationModule;
import net.directparking.ui.history.di.HistoryModule;
import net.directparking.ui.home.di.HomeComponent;
import net.directparking.ui.login.di.LoginComponent;
import net.directparking.ui.login.di.LoginModule;
import net.directparking.ui.logout.di.LogoutModule;
import net.directparking.ui.notification.di.NotificationModule;
import net.directparking.ui.parkingslots.di.ParkingSlotModule;
import net.directparking.ui.profile.di.ProfileModule;
import net.directparking.ui.review.post.di.ReviewComponent;
import net.directparking.ui.review.post.di.ReviewModule;
import net.directparking.ui.review.view.di.ViewReviewComponent;
import net.directparking.ui.review.view.di.ViewReviewModule;
import net.directparking.ui.signup.di.SignupComponent;
import net.directparking.ui.signup.di.SignupModule;

import com.crashlytics.android.Crashlytics;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.gms.ads.MobileAds;


import net.danlew.android.joda.JodaTimeAndroid;
import net.directparking.util.Constants;
import net.directparking.util.CrashlyticsTree;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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
        MobileAds.initialize(this, "ca-app-pub-1419389556712815~5414710103");
        Fabric.with(this, new Crashlytics());

        ButterKnife.setDebug(BuildConfig.DEBUG);
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected void log(int priority, String tag, @NonNull String msg, Throwable t) {
                super.log(priority, "parking_logs_" + tag, msg, t);
            }
        });
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected void log(int priority, String tag, @NonNull String msg, Throwable t) {
                    super.log(priority, "parking_logs_" + tag, msg, t);
                }

            });
        } else {
//            Fabric.with(this, new Crashlytics());

            Timber.plant(new CrashlyticsTree());
        }

        if (disposable == null) {
            disposable = ReactiveNetwork.observeNetworkConnectivity(this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(connectivity -> {
                        if (connectivity.getState() != NetworkInfo.State.CONNECTED) {
                            EventBus.getDefault().post(new NetworkEventBusMessage(Constants.EVENT_CONNECTIVITY_LOST));
                        } else {
                            EventBus.getDefault().post(new NetworkEventBusMessage(Constants.EVENT_CONNECTIVITY_CONNECTED));
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