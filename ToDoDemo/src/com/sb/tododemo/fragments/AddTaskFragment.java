package com.sb.tododemo.fragments;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sb.tododemo.AddTaskActivity;
import com.sb.tododemo.R;
import com.sb.tododemo.databases.MyTodoContentProvider;
import com.sb.tododemo.databases.TodoTable;

/**
 * Fragment for adding tasks.
 * 
 * @author Sourcebits LLC
 * 
 */
public class AddTaskFragment extends Fragment implements OnClickListener {

    /** Tag used for debugging purposes. */
    private static final String TAG = AddTaskActivity.class.getName();

    private EditText            mTaskCategory;
    private EditText            mTaskSummary;
    private EditText            mTaskDescription;

    private Button              mAddTask;
    private Button              mShowTasks;

    public AddTaskFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_add_task, null);
        initViews(fragmentView);
        return fragmentView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_add) {
            if (mTaskCategory.getText() != null && mTaskDescription.getText() != null && mTaskSummary.getText() != null) {
                saveTaskInDB();
            } else {
                Toast.makeText(getActivity(), "Enter all fields", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * This method inflates all the views in the layout.
     */
    private void initViews(final View view) {
        mTaskCategory = (EditText) view.findViewById(R.id.task_category_edittext);
        mTaskSummary = (EditText) view.findViewById(R.id.task_summary_edittext);
        mTaskDescription = (EditText) view.findViewById(R.id.task_description_edittext);
        mAddTask = (Button) view.findViewById(R.id.button_add);
        mAddTask.setOnClickListener(this);

        mShowTasks = (Button) view.findViewById(R.id.button_show_tasks);
        mShowTasks.setOnClickListener(this);
    }

    /**
     * This method saves the newly added task in the database.
     */
    private void saveTaskInDB() {
        ContentValues values = new ContentValues();
        values.put(TodoTable.COLUMN_CATEGORY, mTaskCategory.getText().toString());
        values.put(TodoTable.COLUMN_SUMMARY, mTaskSummary.getText().toString());
        values.put(TodoTable.COLUMN_DESCRIPTION, mTaskDescription.getText().toString());
        Uri insertedUri = getActivity().getContentResolver().insert(MyTodoContentProvider.CONTENT_URI, values);
        if (insertedUri != null) {
            Toast.makeText(getActivity(), "Row inserted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Problem while inserting...", Toast.LENGTH_SHORT).show();
        }

    }

}
