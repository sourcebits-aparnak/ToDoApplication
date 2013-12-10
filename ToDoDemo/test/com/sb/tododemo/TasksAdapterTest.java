package com.sb.tododemo;

import android.app.Activity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v4.widget.CursorAdapter;

import com.sb.tododemo.adapters.TasksAdapter;
import com.sb.tododemo.databases.TodoTable;
import com.sb.tododemo.support.CustomRobolectricTestRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

@RunWith(CustomRobolectricTestRunner.class)
public class TasksAdapterTest {

    private Activity      activity;
    private CursorAdapter cursorAdapter;
    private Cursor        cursor;

    @Before
    public void setUp() throws Exception {

        activity = Robolectric.buildActivity(HomeActivity.class).create().get();
        cursor = setUpCursor();
        cursorAdapter = new TasksAdapter(activity.getApplicationContext(), cursor);
    }

    public Cursor setUpCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[] { TodoTable.COLUMN_ID, TodoTable.COLUMN_CATEGORY, TodoTable.COLUMN_SUMMARY,
                TodoTable.COLUMN_DESCRIPTION }, 3);
        cursor.addRow(new Object[] { "1", "category", "summary", "description" });
        cursor.addRow(new Object[] { "2", "category", "summary", "description" });
        cursor.addRow(new Object[] { "3", "category", "summary", "description" });
        return cursor;
    }

    @Test
    public void testCursorCount() throws Exception {
        Assert.assertEquals(3, cursorAdapter.getCount());
    }

    @Test
    public void testSwapCursor() throws Exception {
        cursorAdapter.swapCursor(null);
        Assert.assertNull(cursorAdapter.getCursor());
        cursorAdapter.swapCursor(cursor);
        Assert.assertNotNull(cursorAdapter.getCursor());
    }

    @Test
    public void testCloseCursor() {
        Cursor cursor = cursorAdapter.getCursor();
        cursor.close();
        Assert.assertTrue(cursor.isClosed());
    }

    @Test
    public void testCursorPosition() throws Exception {
        Cursor cursor = cursorAdapter.getCursor();
        cursor.moveToFirst();
        Assert.assertTrue(cursor.isFirst());
        cursor.moveToLast();
        Assert.assertTrue(cursor.isLast());
        cursor.moveToPosition(2);
        Assert.assertEquals(2, cursor.getPosition());       
    }

    @Test
    public void testCursorData() throws Exception {
        Cursor cursor = cursorAdapter.getCursor();
        cursor.moveToFirst();
        Assert.assertEquals("Data does not match", "category", 
                cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_CATEGORY)));
    }

}
