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
import com.example.indraaguslesmana.wachat.WaChat;
import com.example.indraaguslesmana.wachat.model.UserContact;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 2/8/17.
 */
public class FragmentContact extends Fragment {
    private static final String TAG = FragmentContact.class.getSimpleName();

    private ListView listView;
    private ArrayAdapter<String> userContactArrayAdapter;
    private ArrayList<String> contact;
    private FirebaseDatabase firebaseDatabase;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact = new ArrayList<>();

        firebaseDatabase = WaChat.getmFirebaseDatabase();
        firebaseDatabase.getReference().child(Constant.KEY_USER)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                UserContact userContact = snapshot.getValue(UserContact.class);
                                contact.add(userContact.getName());
                            Log.d(TAG, "onDataChange: name" + userContact.getName());
                        }
                        
                        userContactArrayAdapter.notifyDataSetChanged();
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
        userContactArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_user_item, contact);

        listView = (ListView) rootView.findViewById(R.id.list_user);
        listView.setAdapter(userContactArrayAdapter);

        return rootView;
    }
}
