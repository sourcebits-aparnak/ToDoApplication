package com.sb.tododemo;

import static com.sb.tododemo.support.CustomRobolectricTestRunner.startFragment;
import static org.junit.Assert.assertNotNull;

import android.support.v4.app.Fragment;

import com.sb.tododemo.fragments.AddTaskFragment;
import com.sb.tododemo.fragments.ViewTasksFragment;
import com.sb.tododemo.support.CustomRobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CustomRobolectricTestRunner.class)
public class HomeActivityTest {
    private Fragment addTaskFragment;
    private Fragment viewTasksFragment;

    @Before
    public void setUp() {
        addTaskFragment = new AddTaskFragment();
        viewTasksFragment = new ViewTasksFragment();
        startFragment(addTaskFragment);
        startFragment(viewTasksFragment);

    }

    @Test
    public void addTaskFragmentShouldNotBeNull() throws Exception {
        assertNotNull("AddTaskFragment is NULL", addTaskFragment);
    }

    @Test
    public void viewTasksFragmentShouldNotBeNull() throws Exception {
        assertNotNull("ViewTasksFragment is NULL", viewTasksFragment);
    }

}
