package com.sb.calcsample.tests.databases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowSQLiteDatabase;

@Implements(value = SQLiteDatabase.class, inheritImplementationMethods = true)
public class CustomSQLiteShadow extends ShadowSQLiteDatabase {

    @Implementation
    public Cursor rawQueryWithFactory(SQLiteDatabase.CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable,
            CancellationSignal cancellationSignal) {
        return rawQueryWithFactory(cursorFactory, sql, selectionArgs, editTable);
    }

}
