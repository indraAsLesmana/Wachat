package com.example.indraaguslesmana.wachat.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.Constant;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
import com.example.indraaguslesmana.wachat.WaChat;
import com.example.indraaguslesmana.wachat.activity.ChatActivity;
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
public class FragmentContact extends Fragment {
    private static final String TAG = FragmentContact.class.getSimpleName();

    private ListView listView;
    private ArrayAdapter<String> userContactArrayAdapter;
    private ArrayList<String> contact;
    private ArrayList<String> idlist;
    private HashMap<String, String> contacList = new HashMap<>();

    private FirebaseDatabase firebaseDatabase;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact = new ArrayList<>();
        idlist = new ArrayList<>();

        firebaseDatabase = WaChat.getmFirebaseDatabase();
        firebaseDatabase.getReference().child(Constant.KEY_USER)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                UserContact userContact = snapshot.getValue(UserContact.class);
                                contacList.put(userContact.getUid(), userContact.getName());
                        }
                        String userId = PreferenceUtils.getSinglePrefrence(getActivity(),
                                PreferenceUtils.PREFERENCE_USER_ID);

                        // check, to remove self id
                        if (contacList != null) {
                            if (contacList.containsKey(userId)){
                                contacList.remove(userId);
                            }

                            for (String key : contacList.keySet()){
                                contact.add(contacList.get(key));
                                idlist.add(key);
                            }
                            userContactArrayAdapter.notifyDataSetChanged();
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

        userContactArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_user_item, contact);

        listView = (ListView) rootView.findViewById(R.id.list_user);
        listView.setAdapter(userContactArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userTargetId = idlist.get(position);

                UserContact.UserDetail userTargetChat =
                        new UserContact.UserDetail(idlist.get(position), contact.get(position));

                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(Constant.KEY_USER, userTargetChat);

                if (userTargetChat != null){
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }
}
