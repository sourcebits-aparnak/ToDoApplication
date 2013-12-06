package com.sb.tododemo;

import android.app.Activity;
import android.os.Bundle;

/**
 * Show the tasks of the user.
 * @author Sourcebits LLC
 *
 */
public class ShowTaskActivity extends Activity {
    
    private static final String TAG = ShowTaskActivity.class.getName();   

    @Override
    protected void onCreate(Bundle savedInstanceState) {     
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_tasks);
    }   

}
