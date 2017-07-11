package com.c3cyberclub.p5_todoapp;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewTask extends AppCompatActivity implements DatePickerDialogFragment.OnCompleteListener, TimePickerDialogFragment.OnTimePickedListener{

    private String mDate="";
    private String mTime="";
    private TextView date_textview;
    private TextView time_textview;
    private EditText todo_name;
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
            }
        });
    }


    public void addTodo(String name, String deadline){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHandler.KEY_NAME, name);
        cv.put(DatabaseHandler.KEY_DEADLINE, deadline);
        cv.put(DatabaseHandler.KEY_CREATED_AT, System.currentTimeMillis());
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
        mDate=date;
        date_textview.setText(date);
//        Toast.makeText(this, "date is " + date, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSelected(String time) {
        mTime=time;
        time_textview.setText(time);
//        Toast.makeText(this, "time is " + time, Toast.LENGTH_SHORT).show();
    }
}
