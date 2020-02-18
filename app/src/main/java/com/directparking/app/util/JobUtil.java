package com.directparking.app.util;

import android.content.Context;

import io.hypertrack.smart_scheduler.Job;
import io.hypertrack.smart_scheduler.Job.NetworkType;
import io.hypertrack.smart_scheduler.Job.Type;
import io.hypertrack.smart_scheduler.SmartScheduler;
import io.hypertrack.smart_scheduler.SmartScheduler.JobScheduledCallback;
import timber.log.Timber;

import static com.directparking.app.util.Constants.JOB_TRACK_ORDER_TASK_TAG;
import static com.directparking.app.util.Constants.JOB_UPDATE_LOCATION_TASK_TAG;
import static com.directparking.app.util.Constants.TRACK_ORDER_JOB_ID;
import static com.directparking.app.util.Constants.TRACK_ORDER_JOB_INTERVAL;
import static com.directparking.app.util.Constants.UPDATE_LOCATION_JOB_ID;
import static com.directparking.app.util.Constants.UPDATE_LOCATION_JOB_INTERVAL;


public class JobUtil {

    private JobUtil() {
    }

    public static void addUpdateLocationJob(Context context, long interval, JobScheduledCallback callback) {
        SmartScheduler jobScheduler = SmartScheduler.getInstance(context);

        if (!jobScheduler.contains(UPDATE_LOCATION_JOB_ID)) {
            Job job = new Job.Builder(UPDATE_LOCATION_JOB_ID, callback,
                    Type.JOB_TYPE_HANDLER, JOB_UPDATE_LOCATION_TASK_TAG)
                    .setRequiredNetworkType(NetworkType.NETWORK_TYPE_CONNECTED)
                    .setRequiresCharging(false)
                    .setPeriodic(interval > UPDATE_LOCATION_JOB_INTERVAL ? interval : UPDATE_LOCATION_JOB_INTERVAL)
                    .build();

            if (jobScheduler.addJob(job)) {
                Timber.d("Update location job added");
            }
        } else {
            Timber.d("Update location job already exists!");
        }
    }

    public static void addTrackOrderJob(Context context, long interval, JobScheduledCallback callback) {
        SmartScheduler jobScheduler = SmartScheduler.getInstance(context);

        if (!jobScheduler.contains(TRACK_ORDER_JOB_ID)) {
            Job job = new Job.Builder(TRACK_ORDER_JOB_ID, callback,
                    Type.JOB_TYPE_HANDLER, JOB_TRACK_ORDER_TASK_TAG)
                    .setRequiredNetworkType(NetworkType.NETWORK_TYPE_CONNECTED)
                    .setRequiresCharging(false)
                    .setPeriodic(interval > TRACK_ORDER_JOB_INTERVAL ? interval : TRACK_ORDER_JOB_INTERVAL)
                    .build();

            if (jobScheduler.addJob(job)) {
                Timber.d("Track order job added");
            }
        } else {
            Timber.d("Track order job already exists!");
        }
    }

    public static void removeUpdateLocationJob(Context context) {
        SmartScheduler jobScheduler = SmartScheduler.getInstance(context);

        if (jobScheduler.contains(UPDATE_LOCATION_JOB_ID)) {
            if (jobScheduler.removeJob(UPDATE_LOCATION_JOB_ID)) {
                Timber.d("Update location job removed");
            }
        }
    }

    public static void removeTrackOrderJob(Context context) {
        SmartScheduler jobScheduler = SmartScheduler.getInstance(context);

        if (jobScheduler.contains(TRACK_ORDER_JOB_ID)) {
            if (jobScheduler.removeJob(TRACK_ORDER_JOB_ID)) {
                Timber.d("Track order job removed");
            }
        }
    }
}
