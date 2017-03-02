package com.example.indraaguslesmana.wachat.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.model.Chat_recent_model;

import java.util.List;

/**
 * Created by indraaguslesmana on 3/2/17.
 */

public class ChatRecentAdapter extends ArrayAdapter<Chat_recent_model> {

    public ChatRecentAdapter(Context context, int resource, List<Chat_recent_model> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = ((Activity) getContext()).
                    getLayoutInflater().inflate(R.layout.message_item_history, parent, false);
        }



        return convertView;
    }
}
