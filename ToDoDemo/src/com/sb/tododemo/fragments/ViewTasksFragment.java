package com.sb.tododemo.fragments;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sb.tododemo.R;
import com.sb.tododemo.adapters.TasksAdapter;
import com.sb.tododemo.databases.MyTodoContentProvider;
import com.sb.tododemo.databases.TodoTable;

/**
 * Fragment for displaying the added tasks.
 * 
 * @author Sourcebits LLC
 * 
 */

public class ViewTasksFragment extends Fragment implements LoaderCallbacks<Cursor>, OnItemLongClickListener{

    private static final String TAG = ViewTasksFragment.class.getName();
    private ListView            mTasksListView;
    private TasksAdapter        mTasksAdapter;


    public ViewTasksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_list_layout, container, false);
        mTasksListView = (ListView) view.findViewById(R.id.tasks_list);
        mTasksListView.setOnItemLongClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTasksAdapter = new TasksAdapter(getActivity(), null);
        mTasksListView.setAdapter(mTasksAdapter);       
        getLoaderManager().initLoader(0, null,  this);
    }

    @Override
    public void onResume() {     
        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle values) {
        return new CursorLoader(getActivity(), MyTodoContentProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mTasksAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTasksAdapter.swapCursor(null);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i(TAG, "view id : " + view.getId());
        Dialog dialog = createDialog(String.valueOf(view.getId()));
        dialog.show();
        return true;
    }   

    /**
     * Returns the dialog to show when list item is long clicked.
     * @return
     */
    private Dialog createDialog(final String id) {

        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setTitle("Select task");
        builder.setItems(R.array.list_options, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {

                case 0:                            
                    getEditDialog(id).show();
                    break;

                case 1:                                        
                    int rowsDeleted = getActivity().getContentResolver().delete(MyTodoContentProvider.CONTENT_URI,
                            TodoTable.COLUMN_ID + "=?" , new String[] { id });
                    Log.i(TAG, "Rows deleted : " + rowsDeleted + " id : " + id );
                    if(rowsDeleted > 0) {
                        Toast.makeText(getActivity(), "Task deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Problem in deleting task", Toast.LENGTH_SHORT).show();
                    }
                    break;

                default:
                    break;
                }

            }
        });
        return builder.create();
    }

    
    /**
     * Show the dialog to edit the task.
     * @param id
     */
    private Dialog getEditDialog(final String id) {
        
        final Dialog editDialog = new Dialog(getActivity());
        editDialog.setContentView(R.layout.fragment_add_task);
        editDialog.setTitle("Edit task");
        final EditText mTaskCategory = (EditText) editDialog.findViewById(R.id.task_category_edittext);
        final EditText  mTaskSummary = (EditText) editDialog.findViewById(R.id.task_summary_edittext);
        final EditText  mTaskDescription = (EditText) editDialog.findViewById(R.id.task_description_edittext);
        
        Cursor cursor = getActivity().getContentResolver().query(MyTodoContentProvider.CONTENT_URI, null,
                TodoTable.COLUMN_ID + "=?", new String[] { id }, null);
        cursor.moveToFirst();
        if(cursor != null && (cursor.getCount() > 0)) {
            mTaskCategory.setText(cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_CATEGORY)));
            mTaskSummary.setText(cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_SUMMARY)));
            mTaskDescription.setText(cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_DESCRIPTION)));
        }
        cursor.close();
        
        Button mAddTask = (Button) editDialog.findViewById(R.id.button_add);        
        mAddTask.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(TodoTable.COLUMN_CATEGORY, mTaskCategory.getText().toString());
                values.put(TodoTable.COLUMN_SUMMARY, mTaskSummary.getText().toString());
                values.put(TodoTable.COLUMN_DESCRIPTION, mTaskDescription.getText().toString());                
                int rowsUpdated = getActivity().getContentResolver().update(MyTodoContentProvider.CONTENT_URI, values,
                        TodoTable.COLUMN_ID + "=?", new String[] { id });
                if (rowsUpdated > 0) {
                    Toast.makeText(getActivity(), "Row updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Problem while updating row", Toast.LENGTH_SHORT).show();
                }
                editDialog.dismiss();
            }
        });
        
        return editDialog;
    }
    
    
}
