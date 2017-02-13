package com.example.indraaguslesmana.wachat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.indraaguslesmana.wachat.model.UserContact;

import java.util.ArrayList;

/**
 * Created by indraaguslesmana on 2/13/17.
 */

public class UserListAdapter extends ArrayAdapter<UserContact> {

    public UserListAdapter(Context context, ArrayList<UserContact> userList) {
        super(context, 0, userList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);

       /* View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_user_item, parent, false);
        }*/
    }


}
