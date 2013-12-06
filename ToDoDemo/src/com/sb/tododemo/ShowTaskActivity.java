package com.sb.tododemo;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ListView;

import com.sb.tododemo.adapters.TasksAdapter;
import com.sb.tododemo.databases.MyTodoContentProvider;

/**
 * Show the tasks of the user.
 * @author Sourcebits LLC
 *
 */
public class ShowTaskActivity extends Activity implements LoaderCallbacks<Cursor> {

    private ListView mTasksListView;

    private TasksAdapter mTasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {     
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taks_list_layout);
        mTasksListView = (ListView) findViewById(R.id.tasks_list);
        mTasksAdapter = new TasksAdapter(this, null);
        mTasksListView.setAdapter(mTasksAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {        
        return new CursorLoader(this, MyTodoContentProvider.CONTENT_URI, null, null, null, null);
    }  

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {        
        mTasksAdapter.swapCursor(null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mTasksAdapter.swapCursor(cursor);
    }


}
