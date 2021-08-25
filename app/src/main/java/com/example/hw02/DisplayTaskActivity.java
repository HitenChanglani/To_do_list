//Assignment Name: Homework Assignment 2
//File Name: GroupB8_HW02.zip
//Group: B8
//Group Members: Hiten Changlani & Anisha Kakwani


package com.example.hw02;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DisplayTaskActivity extends AppCompatActivity {

    final public static String DELETE_TASK = "DELETE_TASK";

    TextView displayTaskName, displayTaskDate, displayTaskPriority;
    GregorianCalendar calendar;
    Task a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        setTitle(getResources().getString(R.string.titleDisplayTask));

        displayTaskName = findViewById(R.id.displayTaskName);
        displayTaskDate = findViewById(R.id.displayTaskDate);
        displayTaskPriority = findViewById(R.id.displayTaskPriority);


        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DisplayTaskActivity.this, getResources().getString(R.string.actionCancelled), Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });


        findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(DisplayTaskActivity.this)
                        .setTitle(getResources().getString(R.string.deleteConfirmation))
                        .setMessage(getResources().getString(R.string.alertMessage))
                        .setPositiveButton(getResources().getString(R.string.yesLabel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                setResult(RESULT_OK);
                                finish();
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.noLabel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();


            }
        });


        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.TASK_DATA)){
            a = (Task) getIntent().getSerializableExtra(MainActivity.TASK_DATA);
            calendar = a.deadline;
            String date = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);

            displayTaskName.setText(a.taskName);
            displayTaskDate.setText(date);
            displayTaskPriority.setText(a.priority);
        }
    }
}