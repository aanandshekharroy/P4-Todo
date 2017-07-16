package com.c3cyberclub.p5_todoapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by theseus on 16/7/17.
 */

public class ScheduleTodos extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Toast.makeText(this,"Scheduled: "+jobParameters.getExtras().getString("deadline"),Toast.LENGTH_LONG).show();
        Log.d(ScheduleTodos.class.getSimpleName(),"ran");
        Log.d(ScheduleTodos.class.getSimpleName()
                ,jobParameters.getExtras().getString("deadline"));
        setNotification(jobParameters);
        return false;
    }
    public void setNotification(JobParameters jobParameters){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_done_black_24dp);
        mBuilder.setContentTitle("To do");
//
        mBuilder.setDefaults(Notification.DEFAULT_ALL);
        mBuilder.setContentText(jobParameters.getExtras().getString("todo"));

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//mNotificationManager.de
// notificationID allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());
    }
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
