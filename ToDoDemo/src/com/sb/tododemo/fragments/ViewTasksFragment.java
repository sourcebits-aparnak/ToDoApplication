package com.sb.tododemo.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sb.tododemo.R;
import com.sb.tododemo.adapters.TasksAdapter;
import com.sb.tododemo.databases.MyTodoContentProvider;

/**
 * Fragment for displaying the added tasks.
 * 
 * @author Sourcebits LLC
 * 
 */
public class ViewTasksFragment extends Fragment implements LoaderCallbacks<Cursor> {

    private static final String TAG = ViewTasksFragment.class.getName();
    private ListView            mTasksListView;
    private TasksAdapter        mTasksAdapter;

    public ViewTasksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_list_layout, container, false);
        mTasksListView = (ListView) view.findViewById(R.id.tasks_list);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTasksAdapter = new TasksAdapter(getActivity(), null);
        mTasksListView.setAdapter(mTasksAdapter);
        getLoaderManager().initLoader(0, null, this);
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

}
