package com.sb.tododemo.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.sb.tododemo.AddTaskActivity;
import com.sb.tododemo.R;
import com.sb.tododemo.adapters.TasksAdapter;
import com.sb.tododemo.databases.MyTodoContentProvider;
import com.sb.tododemo.databases.TodoTable;

/**
 * Fragment for tasks.
 * @author Sourcebits LLC
 *
 */
public class TasksFragment extends Fragment implements LoaderCallbacks<Cursor>, OnItemLongClickListener {

    private static final String TAG = "TasksFragment";  

    private ListView mTasksListView;

    private TasksAdapter mTasksAdapter;

    private Dialog mDialog;

    private int viewID;

    public TasksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.taks_list_layout, container, false);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
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

    private void setID(int id) {
        viewID = id;
    }

    private int getID() {
        return viewID;
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
                    Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                    startActivity(intent);                    
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

}
