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
    private ArrayList<String> recentChatlist;
    private ArrayAdapter<String> chatListAdapter;


    String uid = PreferenceUtils.getSinglePrefrence(getActivity(), PreferenceUtils.PREFERENCE_USER_ID);
    List<String> nameResult;

    private static final String TAG = "FragmentChat";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentChatlist = new ArrayList<>();

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
                            recentChatlist.add(snapshot.getKey());
//                            Log.i(TAG, "onDataChange: " + snapshot.getRef());

                            /*for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                Log.d(TAG, "onDataChange: " + snapshot1.getRef());

                                Chat_model chat = snapshot1.child(Constant.KEY_MESSAGE).getValue(Chat_model.class);
                                Log.d(TAG, "onDataChange: " + chat.getmMessages());
                            }*/

                            DatabaseReference referenceMessage =
                                    dataSnapshot.child(recentChatlist.get(index))
                                            .child(Constant.KEY_MESSAGE).getRef();

                            referenceMessage.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Chat_model chat = dataSnapshot.getValue(Chat_model.class);
                                    Log.d(TAG, "onDataChange: " + chat.getmMessages());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

//                            Log.d(TAG, "onDataChange: " + referenceMessage.getRef());

                            index++;
//                          Log.i(TAG, "onDataChange: " + index);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
