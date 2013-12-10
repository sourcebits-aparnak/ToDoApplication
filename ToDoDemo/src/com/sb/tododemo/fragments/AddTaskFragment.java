package com.sb.tododemo.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sb.tododemo.R;
import com.sb.tododemo.databases.MyTodoContentProvider;
import com.sb.tododemo.databases.TodoTable;
import com.sb.tododemo.receivers.TaskReminder;

import java.util.GregorianCalendar;

/**
 * Fragment for adding tasks.
 * 
 * @author Sourcebits LLC
 * 
 */
public class AddTaskFragment extends Fragment implements OnClickListener {

    /** Tag used for debugging purposes. */
    private static final String TAG = AddTaskFragment.class.getName();

    private EditText            mTaskCategory;
    private EditText            mTaskSummary;
    private EditText            mTaskDescription;

    private Button              mAddTask;

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
                Toast.makeText(getActivity(), R.string.error_enter_all_rows, Toast.LENGTH_SHORT).show();
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
    }

    /**
     * This method saves the newly added task in the database.
     */
    private void saveTaskInDB() {
        ContentValues values = new ContentValues();
        final String category = mTaskCategory.getText().toString();
        final String summary = mTaskSummary.getText().toString();
        final String description = mTaskDescription.getText().toString();
        values.put(TodoTable.COLUMN_CATEGORY, category);
        values.put(TodoTable.COLUMN_SUMMARY, summary);
        values.put(TodoTable.COLUMN_DESCRIPTION, description);
        Uri insertedUri = getActivity().getContentResolver().insert(MyTodoContentProvider.CONTENT_URI, values);
        if (insertedUri != null) {
            Toast.makeText(getActivity(), R.string.task_inserted, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.error_problem_while_inserting, Toast.LENGTH_SHORT).show();
        }

        Cursor cursor = getActivity().getContentResolver().query(MyTodoContentProvider.CONTENT_URI, 
                new String[] { TodoTable.COLUMN_ID }, TodoTable.COLUMN_CATEGORY + "=? AND " + TodoTable.COLUMN_SUMMARY + "=? AND " + 
                        TodoTable.COLUMN_DESCRIPTION + "=?", new String[] { category, summary, description}, null);

        String colID = "0";
        if(cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            colID = cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_ID));
            Log.i(TAG, "col id : " + colID);
        }
        cursor.close();
        
        Long time = new GregorianCalendar().getTimeInMillis() + (20 * 1000);
        Intent intent = new Intent(getActivity(), TaskReminder.class);
        intent.putExtra("ID", colID);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        // set the alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(getActivity(), 1, intent, 
                PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(getActivity(), "Alarm scheduled in 20 seconds", Toast.LENGTH_SHORT).show();

    }

}
