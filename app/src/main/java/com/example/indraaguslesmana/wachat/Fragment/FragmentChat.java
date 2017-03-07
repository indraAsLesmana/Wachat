package com.example.indraaguslesmana.wachat.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.Constant;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
import com.example.indraaguslesmana.wachat.WaChat;
import com.example.indraaguslesmana.wachat.adapter.ChatRecentAdapter;
import com.example.indraaguslesmana.wachat.model.Chat_model;
import com.example.indraaguslesmana.wachat.model.Chat_recent_model;
import com.example.indraaguslesmana.wachat.model.UserContact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 2/8/17.
 */
public class FragmentChat extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> chatArrayAdapter;
    private FirebaseDatabase firebaseDatabase;
    private UserContact userContact;
    private String userid;
    private ArrayList<String> recentChatTarget;
    private ArrayList<DatabaseReference> recentChat;
    private ArrayAdapter<String> chatListAdapter;
    private Chat_model chat;
    private Chat_recent_model.UserDetail recentChatView_modelUserDetail;
    private Chat_recent_model.MessageDetail recentChatView_modelMessageDetail;
    private ChatRecentAdapter view_chatrecentAdapter;
    private ArrayList<Chat_recent_model.UserDetail> userDetailArray;
    private ArrayList<Chat_recent_model.MessageDetail> messageDetailArray;
    private ArrayList<Chat_recent_model> arrayChatHistoryResult;


    private int index = 0;


    String uid = PreferenceUtils.getSinglePrefrence(getActivity(), PreferenceUtils.PREFERENCE_USER_ID);
    List<String> nameResult;

    private static final String TAG = "FragmentChat";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentChatTarget = new ArrayList<>();
        recentChat = new ArrayList<>();
        arrayChatHistoryResult = new ArrayList<>();
        userDetailArray = new ArrayList<>();
        messageDetailArray = new ArrayList<>();

        recentChatView_modelUserDetail = new Chat_recent_model.UserDetail();
        recentChatView_modelMessageDetail = new Chat_recent_model.MessageDetail();

        view_chatrecentAdapter = new ChatRecentAdapter(getActivity(), R.layout.message_item_history,
                arrayChatHistoryResult);

        firebaseDatabase = WaChat.getmFirebaseDatabase();
        firebaseDatabase.getReference()
                .child(WaChat.STRUCKTUR_VERSION)
                .child(Constant.KEY_CHAT)
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //loop throught the target uid, and path to message per target uid
                        index = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            recentChatTarget.add(index, snapshot.getKey());
                            Log.d(TAG, "KEY TARGET----------> " + recentChatTarget.get(index));

                            DatabaseReference referenceMessage =
                                    dataSnapshot.child(recentChatTarget.get(index))
                                            .child(Constant.KEY_MESSAGE).getRef();

                            recentChat.add(index, referenceMessage);
                            Log.d(TAG, "TARGET MESSAGE PATH ---------> " + recentChat.get(index));

                            index++;
                        }


                        //loop throught Message Child
                        index = 0;
                        for (int i = 0; i < recentChatTarget.size(); i++) {
                            Log.d(TAG, "result recent target ----> " + recentChatTarget.get(i));
                            Log.d(TAG, "result recent chat ----> " + recentChat.get(i));
                            chat = new Chat_model();

                            Log.d(TAG, "INDEX ----> " + index);

                            firebaseDatabase.getReference()
                                    .child(WaChat.STRUCKTUR_VERSION)
                                    .child(Constant.KEY_CHAT)
                                    .child(uid)
                                    .child(recentChatTarget.get(i))
                                    .child(Constant.KEY_MESSAGE)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            //getting all chat with Target uid
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                chat = snapshot.getValue(Chat_model.class);

                                                recentChatView_modelMessageDetail.setMessage(chat.getMessage());
                                                recentChatView_modelMessageDetail.setTime_stamp(chat.getTime_stamp());

                                                Log.d(TAG, "onDataChange: Message--->" + recentChatView_modelMessageDetail.getMessage());

                                                messageDetailArray.add(recentChatView_modelMessageDetail);
                                            }




                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                            index++;

                        }

                        for (int i = 0; i < recentChatTarget.size(); i++) {
                            // loop for get user Detail
                            Log.d(TAG, "UID TARGET ------->" + i + " " + recentChatTarget.get(i));
                            userContact = new UserContact();

                            firebaseDatabase.getReference()
                                    .child(WaChat.STRUCKTUR_VERSION)
                                    .child(Constant.KEY_USER)
                                    .child(recentChatTarget.get(i))
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            userContact = dataSnapshot.getValue(UserContact.class);

                                            recentChatView_modelUserDetail.setName(userContact.getName());
                                            recentChatView_modelUserDetail.setLastSeen(userContact.getLastSeen());

                                            userDetailArray.add(recentChatView_modelUserDetail);

                                            if (userDetailArray.size() != messageDetailArray.size()){
                                                Toast.makeText(getActivity(), "size message and user is different", Toast.LENGTH_SHORT).show();
                                            }else {
                                                for (int j = 0; j < recentChatTarget.size(); j++) {
                                                    Chat_recent_model chat = new Chat_recent_model();
                                                    chat.setName(userDetailArray.get(j).getName());
                                                    chat.setLastSeen(userDetailArray.get(j).getLastSeen());
                                                    chat.setMessage(messageDetailArray.get(j).getMessage());
                                                    chat.setTime_stamp(messageDetailArray.get(j).getTime_stamp());

                                                    Log.d(TAG, "onDataChange: --->" + chat.getMessage());
                                                    arrayChatHistoryResult.add(chat);
                                                }
                                            }

                                            view_chatrecentAdapter.notifyDataSetChanged();
                                        }


                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                            view_chatrecentAdapter.notifyDataSetChanged();


                        }

/*
                        boolean chatlistNULL = chatList.size() < 1;
                        Log.d(TAG, "CHAT LIST IS NULL? ======= " + chatlistNULL);

                        view_chatrecentAdapter.notifyDataSetChanged();
                        Log.d(TAG, "RESULT ON CREATE " + result_recentChat.size());*/

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        ListView mListChat = (ListView) rootView.findViewById(R.id.list_user);

        mListChat.setAdapter(view_chatrecentAdapter);

        return rootView;
    }

    private String getUserNameById(String userId) {
        firebaseDatabase.getReference()
                .child(WaChat.STRUCKTUR_VERSION)
                .child(Constant.KEY_USER)
                .child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userContact = dataSnapshot.getValue(UserContact.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        return userContact.getName();
    }

}
