package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    private ListView listView;
    private EditText editText;
    private Button sendButton;
    private ArrayList<String> messages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = (ListView) findViewById(R.id.chatListView);
        editText = (EditText) findViewById(R.id.chatEditText);
        sendButton = (Button) findViewById(R.id.chatSendButton);

        //in this case, “this” is the ChatWindow, which is-A Context object
        ChatAdapter messageAdapter = new ChatAdapter( this );
        listView.setAdapter (messageAdapter);

        sendButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messages.add( editText.getText().toString() );
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

}