package com.sb.tododemo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sb.tododemo.R;
import com.sb.tododemo.databases.TodoTable;

/**
 * Cursor Adapter for showing the tasks.
 * @author Sourcebits LLC
 *
 */
public class TasksAdapter extends CursorAdapter {
    
    private static final String TAG = TasksAdapter.class.getName();

    public TasksAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);        
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        
        Log.i(TAG, "Count : " + cursor.getCount());

        if(cursor != null && cursor.getCount() > 0) {

            final TextView task_category = (TextView) view.findViewById(R.id.task_category_textview);
            final TextView task_description = (TextView) view.findViewById(R.id.task_description_textview);
            final TextView task_summary = (TextView) view.findViewById(R.id.task_summary_textview);

            view.setId(cursor.getInt(cursor.getColumnIndex(TodoTable.COLUMN_ID)));
            
            task_category.setText(cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_CATEGORY)));
            task_description.setText(cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_DESCRIPTION)));
            task_summary.setText(cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_SUMMARY)));

        }               

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        
        final View view = LayoutInflater.from(mContext).inflate(R.layout.tasks_list_item, null);
        return view;
    }

}
