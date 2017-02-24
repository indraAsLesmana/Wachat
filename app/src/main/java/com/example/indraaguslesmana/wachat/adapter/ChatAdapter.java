package com.example.indraaguslesmana.wachat.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.Helpers;
import com.example.indraaguslesmana.wachat.model.Chat_model;

import java.util.List;

/**
 * Created by indraaguslesmana on 1/19/17.
 */

public class ChatAdapter extends ArrayAdapter<Chat_model> {

    public ChatAdapter(Context context, int resource, List<Chat_model> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = ((Activity) getContext()).
                    getLayoutInflater().inflate(R.layout.message_item, parent, false);
        }
        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView timeStampTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        Chat_model message = getItem(position);

        boolean isPhoto = message.getmPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getmPhotoUrl())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getmMessages());
        }

        long time = Long.valueOf(message.getmTimeStamp().toString());
        timeStampTextView.setText(Helpers.convertUnixTime(time));

        return convertView;
    }
}
