package com.example.indraaguslesmana.wachat.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.Constant;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
import com.example.indraaguslesmana.wachat.WaChat;
import com.example.indraaguslesmana.wachat.model.Messages_Detail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";
    private final static String CHAT_TARGET_FORCE = "Otbwh0xaopSBGn40021CAD1BuDk2";

    private static final String TIME_STAMP = "timeStamp";

    private ImageButton mSendMessage;
    private ImageButton mTakeGaleri;
    private EditText mEditMessage;
    String uid = PreferenceUtils.getSinglePrefrence(this, PreferenceUtils.PREFERENCE_USER_ID);
    Messages_Detail messages_detail = new Messages_Detail();

    //firebase
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mSendMessage = (ImageButton) findViewById(R.id.send_chat);
        mTakeGaleri = (ImageButton) findViewById(R.id.imageSelect);
        mEditMessage = (EditText) findViewById(R.id.ed_chat);

        //firebase initialize
        firebaseDatabase = WaChat.getmFirebaseDatabase();

        mEditMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: exetution count : 1");

                firebaseDatabase.getReference()
                        .child(Constant.KEY_CHAT)
                        .child(uid)
                        .child(CHAT_TARGET_FORCE)
                        .child(Constant.KEY_TYPING)
                        .setValue(Boolean.TRUE.toString());


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firebaseDatabase.getReference()
                                .child(Constant.KEY_CHAT)
                                .child(uid)
                                .child(CHAT_TARGET_FORCE)
                                .child(Constant.KEY_TYPING)
                                .setValue(Boolean.FALSE.toString());
                    }
                }, 5000);

            }
        });


        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChat();
            }
        });

    }


    private void sendChat() {

        messages_detail.setDestination(CHAT_TARGET_FORCE);
        messages_detail.setMessage(mEditMessage.getText().toString());
        messages_detail.setSender(uid);
        //get server time
        Map<String, Object> time = new HashMap<>();
        time.put(TIME_STAMP, ServerValue.TIMESTAMP);
        messages_detail.setTimeStamp(time.get(TIME_STAMP));
        Log.d(TAG, "sendChat: time" + messages_detail.getTimeStamp());

        //send chat from sender
        firebaseDatabase.getReference()
                .child(Constant.KEY_CHAT)
                .child(uid)
                .child(CHAT_TARGET_FORCE)
                .child(Constant.KEY_MESSAGE)
                .push()
                .setValue(messages_detail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(ChatActivity.this, "send chat failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //make duplicate and change, sender and receiver target
        messages_detail.setDestination(uid);
        messages_detail.setSender(CHAT_TARGET_FORCE);

        firebaseDatabase.getReference()
                .child(Constant.KEY_CHAT)
                .child(CHAT_TARGET_FORCE)
                .child(uid)
                .child(Constant.KEY_MESSAGE)
                .push()
                .setValue(messages_detail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(ChatActivity.this, "sender view failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
