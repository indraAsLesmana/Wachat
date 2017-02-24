package com.example.indraaguslesmana.wachat.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.Constant;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
import com.example.indraaguslesmana.wachat.WaChat;
import com.example.indraaguslesmana.wachat.adapter.ChatAdapter;
import com.example.indraaguslesmana.wachat.model.Messages_Detail;
import com.example.indraaguslesmana.wachat.model.Chat_model;
import com.example.indraaguslesmana.wachat.model.UserContact;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    private static final String TIME_STAMP = "timeStamp";

    private ChatAdapter mChatAdapter;
    private ImageButton mSendMessage;
    private ImageButton mTakeGaleri;
    private EditText mEditMessage;
    private ListView mListChat;
    String uid = PreferenceUtils.getSinglePrefrence(this, PreferenceUtils.PREFERENCE_USER_ID);
    String name = PreferenceUtils.getSinglePrefrence(this, PreferenceUtils.PREFERENCE_USER_NAME);
    Messages_Detail messages_detail = new Messages_Detail();

    //firebase
    private FirebaseDatabase firebaseDatabase;
    private ChildEventListener mChilEventListener;
    private DatabaseReference mDatabasePreference;
    
    private UserContact.UserDetail userTargetChat;
    private String targetUId;
    private String targetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent i = getIntent();
        if (i != null) {
            userTargetChat = (UserContact.UserDetail) i.getSerializableExtra(Constant.KEY_USER);
            targetUId = userTargetChat.getUid();
            targetName = userTargetChat.getName();
        }

        //Change Action Bar title with Name, if private chat
        setTitle(targetName);

        mSendMessage = (ImageButton) findViewById(R.id.send_chat);
        mTakeGaleri = (ImageButton) findViewById(R.id.imageSelect);
        mEditMessage = (EditText) findViewById(R.id.ed_chat);
        mListChat = (ListView) findViewById(R.id.list_chat);

        List<Chat_model> chatModels = new ArrayList<>();
        mChatAdapter = new ChatAdapter(this, R.layout.message_item_layout, chatModels);
        mListChat.setAdapter(mChatAdapter);

        //firebase initialize
        firebaseDatabase = WaChat.getmFirebaseDatabase();
        mDatabasePreference = WaChat.getmDatabaseReferenceCHAT()
                .child(uid)
                .child(targetUId)
                .child(Constant.KEY_MESSAGE);



        mChilEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat_model chatmodel = dataSnapshot.getValue(Chat_model.class);
                mChatAdapter.add(chatmodel);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        mDatabasePreference.addChildEventListener(mChilEventListener);

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

                /*firebaseDatabase.getReference()
                        .child(Constant.KEY_CHAT)
                        .child(uid)
                        .child(userTargetChat)
                        .child(Constant.KEY_TYPING)
                        .setValue(Boolean.TRUE.toString());


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        firebaseDatabase.getReference()
                                .child(Constant.KEY_CHAT)
                                .child(uid)
                                .child(userTargetChat)
                                .child(Constant.KEY_TYPING)
                                .setValue(Boolean.FALSE.toString());
                    }
                }, 5000);*/

            }
        });

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChat();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int actionClick  = item.getItemId();

        switch (actionClick){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void sendChat() {
        DatabaseReference rootPref =
                WaChat.getmDatabaseReferenceCHAT().child(uid).child(userTargetChat.getUid());


        rootPref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(Constant.KEY_NAME)){

                    //send data detail senderId and SenderName
                    firebaseDatabase.getReference()
                            .child(WaChat.STRUCKTUR_VERSION)
                            .child(Constant.KEY_CHAT)
                            .child(uid)
                            .child(targetUId)
                            .setValue(userTargetChat);
                }

                Chat_model chatmodel = new Chat_model();
                chatmodel.setmMessages(mEditMessage.getText().toString());

                Map<String, Object> time = new HashMap<>();
                time.put(TIME_STAMP, ServerValue.TIMESTAMP);
                chatmodel.setmTimeStamp(time.get(TIME_STAMP));
                chatmodel.setSenderId(uid);

                firebaseDatabase.getReference()
                        .child(WaChat.STRUCKTUR_VERSION)
                        .child(Constant.KEY_CHAT)
                        .child(uid)
                        .child(targetUId)
                        .child(Constant.KEY_MESSAGE)
                        .push()
                        .setValue(chatmodel);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //---- duplicate

        DatabaseReference rootPref2 =
                WaChat.getmDatabaseReferenceCHAT().child(userTargetChat.getUid()).child(uid);


        rootPref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(Constant.KEY_NAME)){

                    UserContact.UserDetail userDetail = new UserContact.UserDetail();
                    userDetail.setUid(uid);
                    userDetail.setName(name);

                    //send data detail senderId and SenderName
                    firebaseDatabase.getReference()
                            .child(WaChat.STRUCKTUR_VERSION)
                            .child(Constant.KEY_CHAT)
                            .child(targetUId)
                            .child(uid)
                            .setValue(userDetail);
                }

                Chat_model chatmodel = new Chat_model();
                chatmodel.setmMessages(mEditMessage.getText().toString());

                Map<String, Object> time = new HashMap<>();
                time.put(TIME_STAMP, ServerValue.TIMESTAMP);
                chatmodel.setmTimeStamp(time.get(TIME_STAMP));
                chatmodel.setSenderId(uid);

                firebaseDatabase.getReference()
                        .child(WaChat.STRUCKTUR_VERSION)
                        .child(Constant.KEY_CHAT)
                        .child(targetUId)
                        .child(uid)
                        .child(Constant.KEY_MESSAGE)
                        .push()
                        .setValue(chatmodel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()){
                                    //clear text
                                    Toast.makeText(ChatActivity.this, "message unsend", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
