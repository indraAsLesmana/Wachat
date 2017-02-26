package com.example.indraaguslesmana.wachat.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
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
                    getLayoutInflater().inflate(R.layout.message_item_layout, parent, false);
        }
        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);

        TextView messageTextView_sender = (TextView) convertView.findViewById(R.id.message_text);
//        ImageView arrow_image_sender = (ImageView) convertView.findViewById(R.id.arrow_message_sender);
//        ImageView arrow_image_receiver = (ImageView) convertView.findViewById(R.id.arrow_message_receiver);
        ImageView photoImageView_sender = (ImageView) convertView.findViewById(R.id.photoImageView_sender);
        ImageView photoImageView_receiver = (ImageView) convertView.findViewById(R.id.photoImageView_receiver);

//        TextView messageTextView_receiver = (TextView) convertView.findViewById(R.id.textView_receiver);
//        TextView timeStampTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        Chat_model message = getItem(position);

        boolean isPhoto = message.getmPhotoUrl() != null;

        if (message.getSenderId().equals(PreferenceUtils.getSinglePrefrence(getContext(),
                PreferenceUtils.PREFERENCE_USER_ID))){
            photoImageView_receiver.setVisibility(View.GONE);
//            arrow_image_receiver.setVisibility(View.GONE);

            photoImageView_sender.setVisibility(View.VISIBLE);
//            arrow_image_sender.setVisibility(View.VISIBLE);
            messageTextView_sender.setText(message.getmMessages());

        }else {

            photoImageView_receiver.setVisibility(View.VISIBLE);
//            arrow_image_receiver.setVisibility(View.VISIBLE);

            photoImageView_sender.setVisibility(View.GONE);
//            arrow_image_sender.setVisibility(View.GONE);
            messageTextView_sender.setText(message.getmMessages());

        }


        /*if (isPhoto) {
            messageTextView_sender.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getmPhotoUrl())
                    .into(photoImageView);
        } else {
            messageTextView_sender.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView_sender.setText(message.getmMessages());
        }*/

        /*
        long time = Long.valueOf(message.getmTimeStamp().toString());
        timeStampTextView.setText(Helpers.convertUnixTime(time));
        */

        return convertView;
    }
}
