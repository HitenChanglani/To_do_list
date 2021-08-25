//Assignment Name: Homework Assignment 2
//File Name: GroupB8_HW02.zip
//Group: B8
//Group Members: Hiten Changlani & Anisha Kakwani


package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateTaskActivity extends AppCompatActivity {

    public static final String TASK_DATA = "TASK DATA";
    public static final String DATE_STRING = "DATE_STRING";

    TextView createTaskDateDisplay;
    EditText createTaskEditText;
    RadioGroup radioGroup;
    String priorityOfTask;
    GregorianCalendar date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        setTitle(getResources().getString(R.string.titleCreateTask));

        radioGroup = findViewById(R.id.radioGroup);
        createTaskDateDisplay = findViewById(R.id.createTaskDateDisplay);
        createTaskEditText = findViewById(R.id.createTaskEditText);
        priorityOfTask = "";


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioHigh: priorityOfTask = "High"; break;
                    case R.id.radioMedium: priorityOfTask = "Medium"; break;
                    case R.id.radioLow: priorityOfTask = "Low"; break;
                    default: priorityOfTask = ""; break;
                }
            }
        });


        findViewById(R.id.createTaskCancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CreateTaskActivity.this, getResources().getString(R.string.actionCancelled), Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });


        findViewById(R.id.createTaskSubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = createTaskEditText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra(TASK_DATA, new Task(name, priorityOfTask, date));

                if (name.isEmpty() || priorityOfTask.isEmpty() || createTaskDateDisplay.getText().toString().isEmpty()){
                    Toast.makeText(CreateTaskActivity.this, getResources().getString(R.string.mandatoryFields), Toast.LENGTH_SHORT).show();
                }
                else{
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


        findViewById(R.id.setDateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calender = Calendar.getInstance();
                int year = calender.get(Calendar.YEAR);
                int month = calender.get(Calendar.MONTH);
                int dayOfMonth = calender.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth){
                        createTaskDateDisplay.setText((month+1) + "/" + dayOfMonth + "/" + year);
                        date = new GregorianCalendar(year, month, dayOfMonth);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();

            }
        });

    }
}