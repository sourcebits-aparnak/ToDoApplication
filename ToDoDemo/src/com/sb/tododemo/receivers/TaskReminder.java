package com.sb.tododemo.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import com.sb.tododemo.databases.MyTodoContentProvider;
import com.sb.tododemo.databases.TodoTable;

public class TaskReminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String id = intent.getExtras().getString("ID");
        Cursor cursor = context.getContentResolver().query(MyTodoContentProvider.CONTENT_URI, null, TodoTable.COLUMN_ID + "=?", new String[] { id },
                null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            final String category = cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_CATEGORY));
            final String description = cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_DESCRIPTION));
            final String summary = cursor.getString(cursor.getColumnIndex(TodoTable.COLUMN_SUMMARY));
            Toast.makeText(context, category + "\n" + description + "\n" + summary, Toast.LENGTH_LONG).show();
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

    }

}
