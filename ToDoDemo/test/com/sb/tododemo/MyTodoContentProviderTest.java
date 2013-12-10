package com.sb.tododemo;

import static org.junit.Assert.assertTrue;
import static org.robolectric.Robolectric.shadowOf;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.sb.tododemo.databases.MyTodoContentProvider;
import com.sb.tododemo.databases.TodoTable;
import com.sb.tododemo.support.CustomRobolectricTestRunner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentProvider;
import org.robolectric.shadows.ShadowContentResolver;

@RunWith(CustomRobolectricTestRunner.class)
@Config(shadows = { CustomSQLiteShadow.class })
public class MyTodoContentProviderTest {

    private ContentResolver       mContentResolver;
    private ShadowContentResolver mShadowContentResolver;
    private ShadowContentProvider mShadowContentProvider;

    private MyTodoContentProvider mMyTodoContentProvider;

    @Before
    public void setUp() throws Exception {

        mMyTodoContentProvider = new MyTodoContentProvider();
        mContentResolver = Robolectric.application.getContentResolver();
        mShadowContentResolver = shadowOf(mContentResolver);

        mMyTodoContentProvider.onCreate();

        ShadowContentResolver.registerProvider(MyTodoContentProvider.AUTHORITY, mMyTodoContentProvider);
    }

    @Test
    public void testInsert() throws Exception {
        // Insert a item.
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoTable.COLUMN_CATEGORY, "Category");
        contentValues.put(TodoTable.COLUMN_DESCRIPTION, "description");
        contentValues.put(TodoTable.COLUMN_SUMMARY, "Summary");
        mContentResolver.insert(MyTodoContentProvider.CONTENT_URI, contentValues);

        // test that the item was inserted
        Cursor cursor = mShadowContentResolver.query(MyTodoContentProvider.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        assertTrue(cursor.getCount() == 1);
        cursor.close();
    }

    @Test
    public void testQuery() throws Exception {
        // Insert a item.
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoTable.COLUMN_CATEGORY, "category-1");
        contentValues.put(TodoTable.COLUMN_DESCRIPTION, "description");
        contentValues.put(TodoTable.COLUMN_SUMMARY, "summary");
        mContentResolver.insert(MyTodoContentProvider.CONTENT_URI, contentValues);

        contentValues = null;
        contentValues = new ContentValues();
        contentValues.put(TodoTable.COLUMN_CATEGORY, "category-2");
        contentValues.put(TodoTable.COLUMN_DESCRIPTION, "description");
        contentValues.put(TodoTable.COLUMN_SUMMARY, "summary");
        mContentResolver.insert(MyTodoContentProvider.CONTENT_URI, contentValues);

        Cursor cursor = mShadowContentResolver.query(MyTodoContentProvider.CONTENT_URI, null, null, null, null);
        cursor.getCount();
        assertTrue("Table is not empty. Number of rows in the table : " + cursor.getCount(), cursor.getCount() != 0);
        cursor.close();
    }

    @Test
    public void testUpdateQuery() throws Exception {
        // Insert a item.
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoTable.COLUMN_CATEGORY, "category_1");
        contentValues.put(TodoTable.COLUMN_DESCRIPTION, "description");
        contentValues.put(TodoTable.COLUMN_SUMMARY, "summary");
        mContentResolver.insert(MyTodoContentProvider.CONTENT_URI, contentValues);

        // Insert a item.
        ContentValues values = new ContentValues();
        values.put(TodoTable.COLUMN_CATEGORY, "category-1");
        mContentResolver.insert(MyTodoContentProvider.CONTENT_URI, values);

        int updatedRows = mShadowContentResolver.update(MyTodoContentProvider.CONTENT_URI, values, TodoTable.COLUMN_CATEGORY + " LIKE ?",
                new String[] { "category_1" });
        assertTrue("No such row entry exists", updatedRows > 0);

    }

    @Test
    public void testDeleteRow() throws Exception {
        // Insert a item.
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoTable.COLUMN_CATEGORY, "category_1");
        contentValues.put(TodoTable.COLUMN_DESCRIPTION, "description");
        contentValues.put(TodoTable.COLUMN_SUMMARY, "summary");
        mContentResolver.insert(MyTodoContentProvider.CONTENT_URI, contentValues);

        // Delete the item
        int rowsDeleted = mShadowContentResolver.delete(MyTodoContentProvider.CONTENT_URI, TodoTable.COLUMN_CATEGORY + "=?", 
                new String[] { "category_1" });
        Assert.assertEquals(1, rowsDeleted);

    }

}
