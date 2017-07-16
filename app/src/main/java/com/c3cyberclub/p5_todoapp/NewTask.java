package com.c3cyberclub.p5_todoapp;

import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewTask extends AppCompatActivity implements DatePickerDialogFragment.OnCompleteListener, TimePickerDialogFragment.OnTimePickedListener{

    private String mDate="";
    private String mTime="";
    private TextView date_textview;
    private TextView time_textview;
    private EditText todo_name;
    private int JOB_ID=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        date_textview=(TextView)findViewById(R.id.date_textview);
        time_textview=(TextView)findViewById(R.id.time_textview);
        todo_name=(EditText)findViewById(R.id.todo_name);
        Button save=(Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo(todo_name.getText().toString(),mTime+" "+mDate);
                sheduleJob();
            }
        });
    }

    private void sheduleJob() {
        ComponentName serviceName = new ComponentName(this, ScheduleTodos.class);

        String deadline=mTime+" "+mDate;
        Log.d("deadline: ",""+deadline);
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm dd/M/yyyy");
        Date mDate = null;
        long timeInMilliseconds = 100;
        try {
            mDate = sdf.parse(deadline);
            timeInMilliseconds = mDate.getTime();
            Log.d("milli: ","-"+timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        setNotification();
        PersistableBundle bundle=new PersistableBundle();
        bundle.putString("deadline",deadline);
        bundle.putString("todo",todo_name.getText().toString());
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                .setOverrideDeadline(timeInMilliseconds)
                .setExtras(bundle)
                .build();
        JobScheduler scheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        Log.d("Scheduled:"," "+result);
        if (result==JobScheduler.RESULT_SUCCESS){
            finish();
        }else {
            Toast.makeText(this, "Unable to schedule notification",Toast.LENGTH_LONG).show();
        }
    }

    public void setNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("To do");
        mBuilder.setContentText("Do it");

        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
        mNotificationManager.notify(100, mBuilder.build());
    }
    public void addTodo(String name, String deadline){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHandler.KEY_NAME, name);
        cv.put(DatabaseHandler.KEY_DEADLINE, deadline);
        cv.put(DatabaseHandler.KEY_CREATED_AT, System.currentTimeMillis());
        SQLiteDatabase db = new DatabaseHandler(this).getWritableDatabase();
        db.insert(DatabaseHandler.TABLE_TODOS,null, cv);

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerDialogFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerDialogFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSelected(String date) {
        mDate=date;
        date_textview.setText(date);
    }

    @Override
    public void onTimeSelected(String time) {
        mTime=time;
        time_textview.setText(time);
    }
}
