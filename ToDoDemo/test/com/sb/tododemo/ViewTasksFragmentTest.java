package com.sb.tododemo;

import static org.junit.Assert.assertNotNull;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.sb.tododemo.fragments.ViewTasksFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Testcases for the View Tasks fragment.
 * 
 * @author aparna
 * 
 */
@RunWith(RobolectricTestRunner.class)
public class ViewTasksFragmentTest {

    private Fragment viewTasksFragment;
    private ListView tasksList;

    @Before
    public void setUp() {
        viewTasksFragment = new ViewTasksFragment();
        startFragment(viewTasksFragment);

        tasksList = (ListView) viewTasksFragment.getView().findViewById(R.id.tasks_list);
    }

    private void startFragment(Fragment fragment) {
        FragmentActivity activity = Robolectric.buildActivity(FragmentActivity.class).create().start().resume().get();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
    }

    @Test
    public void fragmentShouldHaveViews() {
        assertNotNull("List is NULL", tasksList);
    }
}
