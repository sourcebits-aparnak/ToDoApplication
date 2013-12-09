package com.sb.tododemo;

import android.app.Activity;
import android.os.Bundle;

public class AddTaskActivity extends Activity {

    private static final String TAG = AddTaskActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_task);
    }

}
