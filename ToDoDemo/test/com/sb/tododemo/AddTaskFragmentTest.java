package com.sb.tododemo;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static com.sb.tododemo.support.CustomRobolectricTestRunner.startFragment;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.sb.tododemo.fragments.AddTaskFragment;
import com.sb.tododemo.support.CustomRobolectricTestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowToast;

/**
 * Testcases for the Add Task fragment.
 * 
 * @author aparna
 * 
 */
@RunWith(CustomRobolectricTestRunner.class)
public class AddTaskFragmentTest {

    private Fragment addTaskFragment;
    private EditText taskCategory;
    private EditText taskSummary;
    private EditText taskDescription;
    private Button   addTaskButton;

    @Before
    public void setUp() {
        addTaskFragment = new AddTaskFragment();
        startFragment(addTaskFragment);

        taskCategory = (EditText) addTaskFragment.getView().findViewById(R.id.task_category_edittext);
        taskSummary = (EditText) addTaskFragment.getView().findViewById(R.id.task_summary_edittext);
        taskDescription = (EditText) addTaskFragment.getView().findViewById(R.id.task_description_edittext);
        addTaskButton = (Button) addTaskFragment.getView().findViewById(R.id.button_add);
    }

    @Test
    public void viewsShouldNotBeNull() throws Exception {
        assertNotNull("taskSummary is null", taskSummary);
        assertNotNull("taskCategory is null", taskCategory);
        assertNotNull("taskDescription is null", taskDescription);
        assertNotNull("addTaskButton is null", addTaskButton);
    }

    @Test
    public void shouldSaveTaskDetailsToTheDatabase() throws Exception {
        taskCategory.setText("Meeting");
        taskSummary.setText("Product Planning");
        taskDescription.setText("Discussion about the product");
        addTaskButton.performClick();
        assertEquals("Expected toast message is not displayed", "Task added", ShadowToast.getTextOfLatestToast());
    }

}
