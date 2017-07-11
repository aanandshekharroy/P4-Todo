package com.c3cyberclub.p5_todoapp;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NewTask extends AppCompatActivity implements DatePickerDialogFragment.OnCompleteListener, TimePickerDialogFragment.OnTimePickedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

    }


    public void addTodo(String name, String deadline, String created_at){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHandler.KEY_NAME, name);
        cv.put(DatabaseHandler.KEY_DEADLINE, deadline);
        cv.put(DatabaseHandler.KEY_CREATED_AT, created_at);
        SQLiteDatabase db = new DatabaseHandler(this).getWritableDatabase();
        db.insert(DatabaseHandler.TABLE_TODOS,null, cv);
        finish();
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
        Toast.makeText(this, "date is " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSelected(String time) {
        Toast.makeText(this, "time is " + time, Toast.LENGTH_SHORT).show();
    }
}
