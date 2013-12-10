package com.sb.tododemo;

import static com.sb.tododemo.support.CustomRobolectricTestRunner.startFragment;
import static org.junit.Assert.assertNotNull;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.sb.tododemo.fragments.ViewTasksFragment;
import com.sb.tododemo.support.CustomRobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testcases for the View Tasks fragment.
 * 
 * @author aparna
 * 
 */
@RunWith(CustomRobolectricTestRunner.class)
public class ViewTasksFragmentTest {

    private Fragment viewTasksFragment;
    private ListView tasksList;

    @Before
    public void setUp() {
        viewTasksFragment = new ViewTasksFragment();
        startFragment(viewTasksFragment);
        tasksList = (ListView) viewTasksFragment.getView().findViewById(R.id.tasks_list);
    }

    @Test
    public void fragmentShouldHaveViews() {
        assertNotNull("List is NULL", tasksList);
    }
}
