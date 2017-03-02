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

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.Constant;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
import com.example.indraaguslesmana.wachat.WaChat;
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
    private ArrayList<Chat_model> chatList;
    private Chat_model chat;
    private UserContact usercontact;
    private Chat_recent_model recentChatView_model;
    private ArrayList<Chat_recent_model> result_recentChat;



    String uid = PreferenceUtils.getSinglePrefrence(getActivity(), PreferenceUtils.PREFERENCE_USER_ID);
    List<String> nameResult;

    private static final String TAG = "FragmentChat";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentChatTarget = new ArrayList<>();
        recentChat = new ArrayList<>();
        chatList = new ArrayList<>();
        result_recentChat = new ArrayList<>();

        firebaseDatabase = WaChat.getmFirebaseDatabase();
        firebaseDatabase.getReference()
                .child(WaChat.STRUCKTUR_VERSION)
                .child(Constant.KEY_CHAT)
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //loop throught the target uid, and path to message per target uid
                        int index = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            recentChatTarget.add(index, snapshot.getKey());
                            Log.d(TAG, "KEY TARGET----------> " + recentChatTarget.get(index));

                            DatabaseReference referenceMessage =
                                    dataSnapshot.child(recentChatTarget.get(index))
                                            .child(Constant.KEY_MESSAGE).getRef();

                            recentChat.add(index, referenceMessage);
                            Log.d(TAG, "TARGET MESSAGE PATH --------> " + recentChat.get(index));

                            index++;
                        }


                        //loop throught Message Child
                        for (int i = 0; i < recentChatTarget.size(); i++) {
                            Log.d(TAG, "result recent target ----> " + recentChatTarget.get(i));
                            Log.d(TAG, "result recent chat ----> " + recentChat.get(i));
                            chat = new Chat_model();

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
                                            }

                                            // last Chat Message
                                            Log.d(TAG, "LAST CHAT MESSAGE ---> " + chat.getMessage());
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                        }

                        for (int i = 0; i < recentChatTarget.size(); i++) {
                            // loop for get user Detail
                            userContact = new UserContact();
                            firebaseDatabase.getReference()
                                    .child(WaChat.STRUCKTUR_VERSION)
                                    .child(Constant.KEY_USER)
                                    .child(recentChatTarget.get(i))
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            usercontact = dataSnapshot.getValue(UserContact.class);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                            recentChatView_model = new Chat_recent_model();
                            if (chat != null && userContact != null){
                                recentChatView_model = new Chat_recent_model(
                                        chat.getMessage(),
                                        chat.getTime_stamp(),
                                        userContact.getLastSeen(),
                                        userContact.getName());
                            }

                            //finish last
                            result_recentChat.add(recentChatView_model);
                        }
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
//        chatListAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_user_item, contact);

        return rootView;
    }

    private String getUserNameById (String userId){
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
