package com.sb.tododemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private Fragment addTaskFragment;

    @Before
    public void setUp() {
        addTaskFragment = new AddTaskFragment();
        startFragment(addTaskFragment);
    }

    private void startFragment(Fragment fragment) {
        FragmentActivity activity = Robolectric.buildActivity(FragmentActivity.class).create().start().resume().get();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
    }

    @Test
    public void addTaskFragmentShouldNotBeNull() throws Exception {
        assertNotNull("Fragment is NULL", addTaskFragment);
    }

}
