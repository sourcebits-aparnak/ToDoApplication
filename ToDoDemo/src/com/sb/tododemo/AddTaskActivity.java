package com.sb.tododemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.sb.tododemo.databases.MyTodoContentProvider;
import com.sb.tododemo.databases.TodoTable;

public class AddTaskActivity extends Activity implements OnClickListener {

    private EditText mTaskCategory;
    private EditText mTaskSummary;
    private EditText mTaskDescription;
    private Button   mAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
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
            // TODO: Save the details to the database.
            final ContentValues values = new ContentValues();
            values.put(TodoTable.COLUMN_CATEGORY, mTaskCategory.getText().toString());
            values.put(TodoTable.COLUMN_SUMMARY, mTaskSummary.getText().toString());
            values.put(TodoTable.COLUMN_DESCRIPTION, mTaskDescription.getText().toString());
            getContentResolver().insert(MyTodoContentProvider.CONTENT_URI, values);
                        
        }

    }

}
