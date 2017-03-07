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
import com.example.indraaguslesmana.wachat.adapter.ChatRecentAdapter;
import com.example.indraaguslesmana.wachat.model.ChatModel_response;
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
    private ArrayAdapter<String> chatListAdapter;
    private ChatRecentAdapter view_chatrecentAdapter;
    private ArrayList<Chat_recent_model> arrayChatHistoryResult;
    private Chat_recent_model chatRecent;

    private int index = 0;

    String uid = PreferenceUtils.getSinglePrefrence(getActivity(), PreferenceUtils.PREFERENCE_USER_ID);
    List<String> nameResult;

    private static final String TAG = "FragmentChat";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentChatTarget = new ArrayList<>();
        arrayChatHistoryResult = new ArrayList<>();

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

                            ChatModel_response response = snapshot.getValue(ChatModel_response.class);

                            Log.d(TAG, "onDataChange: ---> getMessage: " + response.getMessages().getMessage());

                            /*DatabaseReference referenceMessage =
                                    dataSnapshot.child(recentChatTarget.get(index))
                                            .child(Constant.KEY_MESSAGE).getRef();

                            referenceMessage
                                    .orderByKey()
                                    .limitToLast(1);

                            referenceMessage.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snap : dataSnapshot.getChildren()){
                                        Chat_model messageDetail = snap
                                                .getValue(Chat_model.class);
                                        Log.d(TAG, "onDataChange: Messagge ----> "
                                                + messageDetail.getMessage());
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/

                            index++;
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
