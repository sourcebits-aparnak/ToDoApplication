package com.sb.tododemo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import com.sb.tododemo.support.CustomRobolectricTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

@RunWith(CustomRobolectricTestRunner.class)
public class NamesActivityTest {
    @Test
    public void shouldShowAListOfNames() throws Exception {
        Activity activity = Robolectric.buildActivity(NamesActivity.class).create().get();

        ListView namesListView = (ListView) activity.findViewById(R.id.names_list);

        // ListViews only create as many children as will fit in their bounds,
        // so make it big...
        namesListView.layout(0, 0, 100, 1000);

        TextView nameRow = (TextView) namesListView.getChildAt(1);
        assertThat(nameRow.getText().toString(), equalTo("Donald Rumsfeld"));
    }
}
