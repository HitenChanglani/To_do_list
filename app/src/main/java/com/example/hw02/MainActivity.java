package com.example.hw02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    public static final int REQ_CREATE = 100;
    public static final int REQ_DISPLAY = 200;
    public static final String TASK_DATA = "TASK_DATA";

    TextView NoOfTasksLabel, upcomingTasks, taskNameMain, taskDateMain, taskPriorityMain;
    ArrayList<Task> tasks = new ArrayList<>();
    String noOfTasks, deadline;
    int taskIndex;
    GregorianCalendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.titleMainActivity));

        NoOfTasksLabel = findViewById(R.id.NoOfTasksLabel);
        upcomingTasks = findViewById(R.id.upcomingTasks);
        taskNameMain = findViewById(R.id.taskNameMain);
        taskDateMain = findViewById(R.id.taskDateMain);
        taskPriorityMain = findViewById(R.id.taskPriorityMain);

        taskNameMain.setText(getResources().getString(R.string.none));

        noOfTasks = "You have " + tasks.size() + " tasks";
        NoOfTasksLabel.setText(noOfTasks);


        findViewById(R.id.createTaskButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivityForResult(intent, REQ_CREATE);
            }
        });


        findViewById(R.id.viewTasksButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getResources().getString(R.string.selectTask))
                        .setNegativeButton(R.string.cancelButton, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Toast.makeText(MainActivity.this, getResources().getString(R.string.actionCancelled), Toast.LENGTH_SHORT).show();
                            }
                        });


                String[] listOfTasks = new String[tasks.size()];
                for (int i = 0; i < tasks.size(); i++){
                    listOfTasks[i] = tasks.get(i).taskName;
                }

                builder.setItems(listOfTasks, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskIndex = which;
                        Intent intent = new Intent(MainActivity.this, DisplayTaskActivity.class);
                        intent.putExtra(MainActivity.TASK_DATA, tasks.get(which));
                        startActivityForResult(intent, REQ_DISPLAY);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CREATE){
            if (resultCode == RESULT_OK){
                if (data != null && data.hasExtra(CreateTaskActivity.TASK_DATA)){
                    Task task = (Task) data.getSerializableExtra(CreateTaskActivity.TASK_DATA);
                    tasks.add(new Task(task.taskName, task.priority, task.deadline));
                    noOfTasks = "You have " + tasks.size() + " tasks";
                    NoOfTasksLabel.setText(noOfTasks);
                }
            }
        }
        else if (requestCode == REQ_DISPLAY){
            if (resultCode == RESULT_OK){
                    tasks.remove(taskIndex);
                    noOfTasks = "You have " + tasks.size() + " tasks";
                    NoOfTasksLabel.setText(noOfTasks);
            }
        }


        if (tasks.size() == 0){
            taskNameMain.setText(getResources().getString(R.string.none));
            taskDateMain.setText("");
            taskPriorityMain.setText("");
        }
        else if (tasks.size() == 1){
            taskNameMain.setText(tasks.get(0).taskName);
            taskPriorityMain.setText(tasks.get(0).priority);
            calendar = tasks.get(0).deadline;
            deadline = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
            taskDateMain.setText(deadline);
        }
        else{
            ArrayList<Task> sortingList = tasks;
            Collections.sort(sortingList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    return o1.deadline.compareTo(o2.deadline);
                }
            });
            taskNameMain.setText(sortingList.get(0).taskName);
            taskPriorityMain.setText(sortingList.get(0).priority);
            calendar = sortingList.get(0).deadline;
            deadline = (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR);
            taskDateMain.setText(deadline);
        }

    }

}