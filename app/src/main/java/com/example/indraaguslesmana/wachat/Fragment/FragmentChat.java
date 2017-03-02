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
import com.example.indraaguslesmana.wachat.model.Chat_model2;
import com.example.indraaguslesmana.wachat.model.UserContact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
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


    String uid = PreferenceUtils.getSinglePrefrence(getActivity(), PreferenceUtils.PREFERENCE_USER_ID);
    List<String> nameResult;

    private static final String TAG = "FragmentChat";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentChatTarget = new ArrayList<>();
        recentChat = new ArrayList<>();
        chatList = new ArrayList<>();

        firebaseDatabase = WaChat.getmFirebaseDatabase();
        firebaseDatabase.getReference()
                .child(WaChat.STRUCKTUR_VERSION)
                .child(Constant.KEY_CHAT)
                .child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

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

                        /*for (int i = 0; i < recentChatTarget.size(); i++) {
                            Log.d(TAG, "result recent target ----> " + recentChatTarget.get(i));
                            Log.d(TAG, "result recent chat ----> " + recentChat.get(i));
                        }*/

                        for (int i = 0; i < recentChatTarget.size(); i++) {
                            Log.d(TAG, "result recent target ----> " + recentChatTarget.get(i));
                            Log.d(TAG, "result recent chat ----> " + recentChat.get(i));

                            firebaseDatabase.getReference()
                                    .child(WaChat.STRUCKTUR_VERSION)
                                    .child(Constant.KEY_CHAT)
                                    .child(uid)
                                    .child(recentChatTarget.get(i))
                                    .child(Constant.KEY_MESSAGE)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Chat_model chat = dataSnapshot.getValue(Chat_model.class);
                                            Log.d(TAG, "CHAT RESULT ---->" + chat.getMessage());
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

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

    private Chat_model2 jsonParse (String stringSource){

        JSONObject baseResponse = null;

        if (stringSource.isEmpty() || stringSource.length() == 0){
            return null;
        }

        try {
             baseResponse = new JSONObject(stringSource.trim());
            Log.d(TAG, "jsonParse: baseResponse" + baseResponse + " length "+ baseResponse.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Chat_model2 chat = null;

        if (baseResponse.length() > 0){
            for (int i = 0; i < baseResponse.length(); i++) {

                try {

                    Iterator<String> keys = baseResponse.keys();
                    while (keys.hasNext()){
                        String key = keys.next();
                        if ( baseResponse.get(key) instanceof JSONObject ) {
                            JSONObject xx = new JSONObject(baseResponse.get(key).toString());

                            String sender = xx.getString("senderId");
                            String timeStamp = xx.getString("mTimeStamp");
                            String messages = xx.getString("mMessages");

                            chat = new Chat_model2(messages, timeStamp, sender);

                        }
                    }

                    /*String sender = baseResponse.getJSONObject("-Ke7HbX06Z0W-Bo9jNl1").getString("senderId");
                    String timeStamp = baseResponse.getJSONObject("-Ke7HbX06Z0W-Bo9jNl1").getString("mTimeStamp");
                    String messages = baseResponse.getJSONObject("-Ke7HbX06Z0W-Bo9jNl1").getString("mMessages");*/

//                    Log.d(TAG, "jsonParse: " + sender + timeStamp + messages);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        return chat;
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
