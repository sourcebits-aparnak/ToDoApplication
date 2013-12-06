package com.sb.tododemo.fragments;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sb.tododemo.R;
import com.sb.tododemo.adapters.TasksAdapter;
import com.sb.tododemo.databases.MyTodoContentProvider;

/**
 * Fragment for tasks.
 * @author Sourcebits LLC
 *
 */
public class TasksFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final String TAG = TasksFragment.class.getName();  

    private ListView mTasksListView;

    private TasksAdapter mTasksAdapter;
    
    public TasksFragment() {
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.taks_list_layout, container, false);
        mTasksListView = (ListView) view.findViewById(R.id.tasks_list);    
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

}
