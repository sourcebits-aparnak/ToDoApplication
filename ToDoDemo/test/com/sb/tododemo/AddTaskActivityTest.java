package com.sb.tododemo;

import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AddTaskActivityTest {
    private Activity activity;
    private EditText taskCategory;
    private EditText taskSummary;
    private EditText taskDescription;
    private Button   addTaskButton;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AddTaskActivity.class).create().get();
        taskCategory = (EditText) activity.findViewById(R.id.task_category_edittext);
        taskSummary = (EditText) activity.findViewById(R.id.task_summary_edittext);
        taskDescription = (EditText) activity.findViewById(R.id.task_description_edittext);
        addTaskButton = (Button) activity.findViewById(R.id.button_add);
    }

    @Test
    public void viewsShouldNotBeNull() {
        assertNotNull("taskCategory is null", taskCategory);
        assertNotNull("taskSummary is null", taskSummary);
        assertNotNull("taskDescription is null", taskDescription);
        assertNotNull("addTaskButton is null", addTaskButton);
    }

}
