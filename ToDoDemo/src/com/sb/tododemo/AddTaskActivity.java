package com.sb.tododemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sb.tododemo.databases.MyTodoContentProvider;
import com.sb.tododemo.databases.TodoTable;

public class AddTaskActivity extends Activity implements OnClickListener {

    private static final String TAG = AddTaskActivity.class.getName();

    private EditText mTaskCategory;
    private EditText mTaskSummary;
    private EditText mTaskDescription;
    private Button   mAddTask;
    private Button   mShowTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_task);
        initViews();
    }

    /**
     * This method initializes all the views in the layout.
     */
    private void initViews() {
        mTaskCategory = (EditText) findViewById(R.id.task_category_edittext);
        mTaskSummary = (EditText) findViewById(R.id.task_summary_edittext);
        mTaskDescription = (EditText) findViewById(R.id.task_description_edittext);
        mAddTask = (Button) findViewById(R.id.button_add);
        mAddTask.setOnClickListener(this);

        mShowTasks = (Button) findViewById(R.id.button_show_tasks);
        mShowTasks.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_add) {

            ContentValues values = new ContentValues();
            values.put(TodoTable.COLUMN_CATEGORY, mTaskCategory.getText().toString());
            values.put(TodoTable.COLUMN_SUMMARY, mTaskSummary.getText().toString());
            values.put(TodoTable.COLUMN_DESCRIPTION, mTaskDescription.getText().toString());
            Uri insertedUri = getContentResolver().insert(MyTodoContentProvider.CONTENT_URI, values);
            if (insertedUri != null) {
                Toast.makeText(this, "Row inserted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Problem while inserting...", Toast.LENGTH_SHORT).show();
            }

        } else if(view.getId() == R.id.button_show_tasks) {
            Intent intent = new Intent(AddTaskActivity.this, ShowTaskActivity.class);
            startActivity(intent);            

        }

    }

}
