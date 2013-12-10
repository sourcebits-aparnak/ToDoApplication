package com.sb.tododemo.support;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import org.junit.runners.model.InitializationError;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Customized RobolectricTestRunner class which comprises of the utility methods
 * that could be used across multiple test classes.
 * 
 * @author aparna
 * 
 */
public class CustomRobolectricTestRunner extends RobolectricTestRunner {

    public CustomRobolectricTestRunner(Class<?> testClassName) throws InitializationError {
        super(testClassName);
    }

    /**
     * This is a utility method that starts a fragment.
     * 
     * @param fragment
     */
    public static void startFragment(Fragment fragment) {
        FragmentActivity activity = Robolectric.buildActivity(FragmentActivity.class).create().start().resume().get();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null);
        fragmentTransaction.commit();
    }

    /**
     * Fetches the string corresponding to the given resourceId.
     * 
     * @param resourceId
     * @return
     */
    public static String getResourceString(int resourceId) {
        return Robolectric.application.getApplicationContext().getString(resourceId);
    }

    /**
     * Fetches the drawable resource corresponding to the given resourceId.
     * 
     * @param resourceId
     * @return
     */
    public static Drawable getResourceDrawable(int resourceId) {
        return Robolectric.application.getApplicationContext().getResources().getDrawable(resourceId);
    }

    /**
     * Determines if the given view is visible or not.
     * 
     * @param view
     */
    public static void assertViewIsVisible(View view) {
        assertNotNull(view);
        assertThat(view.getVisibility(), equalTo(View.VISIBLE));
    }

    public static void assertViewIsHidden(View view) {
        assertNotNull(view);
        assertThat(view.getVisibility(), equalTo(View.GONE));
    }
}
