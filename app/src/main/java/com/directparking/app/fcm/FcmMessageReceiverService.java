package com.directparking.app.fcm;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.directparking.app.BaseApplication;
import com.directparking.app.R;
import com.directparking.app.data.user.UserManager;
import com.directparking.app.ui.chat.messages.MessageActivity;
import com.directparking.app.ui.home.HomeActivity;
import com.directparking.app.ui.login.model.UserData;
import com.directparking.app.ui.notification.model.NotificationItem;
import com.directparking.app.ui.review.post.ReviewActivity;
import com.directparking.app.ui.splash.SplashActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import timber.log.Timber;

import static com.directparking.app.util.Constants.NOTIFICATION_CHAT;

public class FcmMessageReceiverService extends FirebaseMessagingService {

    @Inject
    UserManager userManager;


    @Override
    public void onCreate() {
        super.onCreate();
        ((BaseApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Timber.d("Received push message: %s", remoteMessage);
//        Map<String, String> hs = remoteMessage.getData();
//        String chatDataa = new Gson().toJson(hs);
//        Timber.d("Received push message: %s", chatDataa);

        if (remoteMessage.getData() != null) {
            Timber.d("Remote message data: %s", remoteMessage.getData().toString());
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String timestamp = remoteMessage.getData().get("timestamp");

            if (title != null && body != null) {
                if (remoteMessage.getData().get("type") != null) {
                    if (remoteMessage.getData().get("type").equals(NOTIFICATION_CHAT)) {
                        NotificationItem item = new NotificationItem(title, "", body, timestamp, "", "");
                        showChatNotification(item, remoteMessage.getData());
                    }
                } else {
                    NotificationItem item = new NotificationItem(title, "", body, timestamp, "", "");
                    showNotification(item);
                }
            }

            if (remoteMessage.getData().get("title").equals("New Ride Request")) {
                broadcastIntent("new_request");
            } else if (remoteMessage.getData().get("body").equals("Your Ride has been completed successfully")) {
                broadcastIntent("complete");
//                Intent intent = new Intent(this, ReviewActivity.class);
//                intent.setAction(Intent.ACTION_DEFAULT);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.putExtra("rideId", remoteMessage.getData().get("rideId"));
//                intent.putExtra("totalTime", remoteMessage.getData().get("totalTime"));
//                intent.putExtra("isRider", remoteMessage.getData().get("isRider"));
//                intent.putExtra("relatedId", remoteMessage.getData().get("userId"));
//                startActivity(intent);
            } else if (remoteMessage.getData().get("body").equals("Your Ride Request has been accepted")) {
                broadcastIntent("accepted");
            } else if (remoteMessage.getData().get("body").equals("Your Ride has been started successfully")) {
                broadcastIntent("started");
            } else if (remoteMessage.getData().get("body").equals("Sorry, the driver has cancelled on your ride request. Would you like to resend a new ride request?")
                    || remoteMessage.getData().get("body").equals("Sorry, the rider has cancelled their ride request. Would you like to search for a new ride request?")) {
                cancelBroadcastIntent(remoteMessage);
            } else if (remoteMessage.getData().get("type") != null) {
                if (remoteMessage.getData().get("type").equals(NOTIFICATION_CHAT)) {
                    if (!isAppIsInBackground(this)) {
                        Map<String, String> h = remoteMessage.getData();
                        String chatData = new Gson().toJson(h);
                        broadcastIntentUpateChatBox(chatData);

                    } else {

                    }
                }
            }
        }
    }

    public void broadcastIntent(String action) {
        Intent intent = new Intent("unique_name");
        intent.putExtra("message", action);
        this.sendBroadcast(intent);
    }

    public void cancelBroadcastIntent(RemoteMessage remoteMessage) {
        Intent intent = new Intent("unique_name");
        intent.putExtra("message", "canceled");

        intent.putExtra("body", remoteMessage.getData().get("body"));
        intent.putExtra("reason", remoteMessage.getData().get("reason"));
        this.sendBroadcast(intent);
    }

    public void broadcastIntentUpateChatBox(String data) {
        Intent intent = new Intent("user_chat");
        intent.putExtra("chatData", data);
        this.sendBroadcast(intent);
    }

    private void showNotification(NotificationItem item) {
        Timber.d("Showing notification...");

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            Intent intent;
            if (userManager.isLoggedIn()) {
                intent = new Intent(this, HomeActivity.class);
                intent.setAction(Intent.ACTION_DEFAULT);
            } else {
                intent = new Intent(this, SplashActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle(item.getTitle())
                    .setContentText(item.getDescription())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.mipmap.ic_notification_transparent);
            } else {
                builder.setSmallIcon(R.mipmap.ic_notification);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());
        } else {
            Timber.e("Notification Manager is null");
        }
    }

    private void showChatNotification(NotificationItem item, Map<String, String> data) {
        Timber.d("Showing chat notification...");

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            Intent intent;
            if (userManager.isLoggedIn()) {
                intent = new Intent(this, MessageActivity.class);
                intent.setAction(Intent.ACTION_DEFAULT);
            } else {
                intent = new Intent(this, SplashActivity.class);
            }
            Bundle bundle = new Bundle();
            UserData userData = new UserData.Builder()
                    .setUserId(data.get("fromUserId"))
                    .setFirstName(data.get("fname"))
                    .setLastName(data.get("lname"))
                    .build();

            bundle.putSerializable("userData", userData);
            bundle.putString("rideId", data.get("rideId"));
            intent.putExtras(bundle);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle(item.getTitle())
                    .setContentText(item.getDescription())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(item.getDescription()))
                    .setSound(defaultSoundUri)
                    .setGroupSummary(true)
                    .setGroup("CHAT")
                    .setAutoCancel(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.mipmap.ic_notification_transparent);
            } else {
                builder.setSmallIcon(R.mipmap.ic_notification);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());
        } else {
            Timber.e("Notification Manager is null");
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    private final AtomicInteger c = new AtomicInteger(0);

    public int getID() {
        return c.incrementAndGet();
    }
}