package com.sb.tododemo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sb.tododemo.AddTaskActivity;

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
        // TODO Auto-generated method stub
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View view) {

    }

}
