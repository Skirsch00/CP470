package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindowActivity";
    private ListView listView;
    private EditText editText;
    private Button sendButton;
    private ArrayList<String> messages = new ArrayList<String>();
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.chatListView);
        editText = (EditText) findViewById(R.id.chatEditText);
        sendButton = (Button) findViewById(R.id.chatSendButton);

        //in this case, “this” is the ChatWindow, which is-A Context object
        ChatAdapter messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);



        ChatDatabaseHelper cdh = new ChatDatabaseHelper(this);
        db = cdh.getWritableDatabase();
        //Cursor cursor = db.rawQuery("SELECT ? FROM ?", new String[] {cdh.KEY_MESSAGE, cdh.TABLE_NAME});
        Cursor cursor = db.rawQuery("SELECT " + cdh.KEY_MESSAGE + " FROM " + cdh.TABLE_NAME, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            messages.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s column count = " + cursor.getColumnCount());
            cursor.moveToNext();
        }
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String colName = cursor.getColumnName(i);
            Log.i(ACTIVITY_NAME, "Column name: " + colName);
        }
        ContentValues cValues = new ContentValues();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                messages.add(message);
                cValues.put(cdh.KEY_MESSAGE, message);
                db.insert(cdh.TABLE_NAME, "NullPlaceholder", cValues);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/
                editText.setText("");
            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String>
    {
        public ChatAdapter(Context ctx)
        {
            super(ctx, 0);
        }

        public int getCount()
        {
            return messages.size();
        }

        public String getItem(int position)
        {
            return messages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            TextView message = null;

            if(position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
                message = (TextView)result.findViewById(R.id.outgoingMessageText);
            }
            else {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                message = (TextView)result.findViewById(R.id.incomingMessageText);
            }

            message.setText( getItem(position) ); // get the string at position

            return result;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}