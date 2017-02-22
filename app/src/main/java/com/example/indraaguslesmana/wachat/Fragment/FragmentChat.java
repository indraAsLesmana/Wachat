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
import com.example.indraaguslesmana.wachat.model.Messages_Detail;
import com.example.indraaguslesmana.wachat.model.UserContact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by indraaguslesmana on 2/8/17.
 */
public class FragmentChat extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> chatArrayAdapter;
    private ArrayList<String> chatArrayList;
    private ArrayList<String> listUid;
    private FirebaseDatabase firebaseDatabase;
    private UserContact userContact;
    private String userid;

    List<String> nameResult;

    private static final String TAG = "FragmentChat";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatArrayList = new ArrayList<>();
        listUid = new ArrayList<>();
        nameResult = new ArrayList<>();
        userContact = new UserContact();

        userid = PreferenceUtils.getSinglePrefrence(getActivity(), PreferenceUtils.PREFERENCE_USER_ID);

        firebaseDatabase = WaChat.getmFirebaseDatabase();
        firebaseDatabase.getReference()
                .child(Constant.KEY_CHAT)
                .child(userid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            listUid.add(snapshot.getKey());

                        }

                        /*for (String key : dataSnapshot.getKey()){
                            String key = snapshot.getKey();
                            Log.d(TAG, "onDataChange: " + snapshot.getChildrenCount());
                            listUid.add(key);

                        }*/

                        // TODO must be add correctyly to ...
                        /*if (listUid != null) {
                            Log.d(TAG, "onDataChange: " + listUid.toString());
                            for (String targetUser : listUid){
                                String name = getUserNameById(targetUser);
                                nameResult.add(name);
                            }
                            chatArrayList.addAll(nameResult);
                            chatArrayAdapter.notifyDataSetChanged();
                        }*/
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
        /*chatArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_user_item, chatArrayList);

        listView = (ListView) rootView.findViewById(R.id.list_user);
        listView.setAdapter(chatArrayAdapter);*/

        return rootView;
    }

    private String getUserNameById (String userId){
            firebaseDatabase.getReference()
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
