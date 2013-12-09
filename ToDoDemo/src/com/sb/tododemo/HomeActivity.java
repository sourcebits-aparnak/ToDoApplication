package com.sb.tododemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TabHost.OnTabChangeListener;

import com.sb.tododemo.fragments.AddTaskFragment;
import com.sb.tododemo.fragments.ViewTasksFragment;
import com.sb.tododemo.widgets.SavedStateFragmentTabHost;

/**
 * Activity from which the user can switch between Adding/Viewing tasks.
 * 
 * @author aparna
 * 
 */
public class HomeActivity extends FragmentActivity implements OnTabChangeListener {

    /** Tag used for debugging purposes */
    private static final String       TAG = HomeActivity.class.getSimpleName();
    private SavedStateFragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home_activity);
        mTabHost = (SavedStateFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        setUpTabs();
        mTabHost.setOnTabChangedListener(this);
    }

    /**
     * Set up the tabs to be toggled between.
     */
    private void setUpTabs() {
        mTabHost.addTab(mTabHost.newTabSpec("addTask").setIndicator("Add Task"), AddTaskFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("viewTask").setIndicator("View Tasks"), ViewTasksFragment.class, null);
    }

    @Override
    public void onTabChanged(String tabId) {

    }

}
