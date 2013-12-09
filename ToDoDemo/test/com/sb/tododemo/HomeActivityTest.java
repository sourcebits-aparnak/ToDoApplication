package com.sb.tododemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.sb.tododemo.fragments.AddTaskFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;

@RunWith(RobolectricTestRunner.class)
public class HomeActivityTest {
    private Activity activity;
    private Fragment fragment;
    private EditText taskCategory;
    private EditText taskSummary;
    private EditText taskDescription;
    private Button   addTaskButton;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(HomeActivity.class).create().get();

        taskCategory = (EditText) activity.findViewById(R.id.task_category_edittext);
        taskSummary = (EditText) activity.findViewById(R.id.task_summary_edittext);
        taskDescription = (EditText) activity.findViewById(R.id.task_description_edittext);
        addTaskButton = (Button) activity.findViewById(R.id.button_add);
    }

    // @Test
    // public void addTaskFragmentShouldNotBeNull() throws Exception {
    // fragment = new AddTaskFragment();
    //
    // }

    @Test
    public void viewsShouldNotBeNull() throws Exception {
        assertNotNull("taskCategory is null", taskCategory);
        assertNotNull("taskSummary is null", taskSummary);
        assertNotNull("taskDescription is null", taskDescription);
        assertNotNull("addTaskButton is null", addTaskButton);
    }

    @Test
    public void shouldSaveTaskDetailsToTheDatabase() throws Exception {
        taskCategory.setText("Meeting");
        taskSummary.setText("Product Planning");
        taskDescription.setText("Discussion about the product");
        addTaskButton.performClick();
        assertEquals("Expected toast message is not displayed", "Row inserted!", ShadowToast.getTextOfLatestToast());
    }

}
