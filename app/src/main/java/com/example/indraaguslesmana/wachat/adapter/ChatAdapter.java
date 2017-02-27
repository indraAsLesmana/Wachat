package com.example.indraaguslesmana.wachat.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.Helpers;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
import com.example.indraaguslesmana.wachat.model.Chat_model;

import java.util.List;

/**
 * Created by indraaguslesmana on 1/19/17.
 */

public class ChatAdapter extends ArrayAdapter<Chat_model> {

    private static String lastTime = "";

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

        LinearLayout senderLayout = (LinearLayout) convertView.findViewById(R.id.layout_sender);
        LinearLayout receiverLayout = (LinearLayout) convertView.findViewById(R.id.layout_receiver);
        TextView date_separator = (TextView) convertView.findViewById(R.id.date_separator);


        Chat_model message = getItem(position);
        long time = Long.valueOf(message.getmTimeStamp().toString());
        String timeResult = Helpers.convertUnixTime(time, Helpers.DATE_STYLE_SEPARATOR);

        // create separator logic.
        if (!timeResult.equals(lastTime)){
            date_separator.setVisibility(View.VISIBLE);
            date_separator.setText(timeResult);
            lastTime = timeResult;
        } else {
            date_separator.setVisibility(View.GONE);
            lastTime = timeResult;
        }

        boolean isPhoto = message.getmPhotoUrl() != null;

        if (message.getSenderId().equals(PreferenceUtils.getSinglePrefrence(getContext(),
                PreferenceUtils.PREFERENCE_USER_ID))){
            //setLayout
            senderLayout.setVisibility(View.VISIBLE);
            receiverLayout.setVisibility(View.GONE);

            TextView messageTextView_sender = (TextView) convertView.findViewById(R.id.message_text);
            ImageView photoImageView_sender = (ImageView) convertView.findViewById(R.id.photoImageView_sender);
            TextView timeStampTextView = (TextView) convertView.findViewById(R.id.time_stamp_sender);

            photoImageView_sender.setVisibility(View.VISIBLE);
            messageTextView_sender.setText(message.getmMessages());

            timeStampTextView.setText(Helpers.convertUnixTime(time, Helpers.DATE_STYLE_ONLY_TIME));

        }else {
            //setLayout
            senderLayout.setVisibility(View.GONE);
            receiverLayout.setVisibility(View.VISIBLE);

            TextView messageTextView_receiver = (TextView) convertView.findViewById(R.id.message_text_2);
            ImageView photoImageView_receiver = (ImageView) convertView.findViewById(R.id.photoImageView_receiver);
            TextView timeStampTextView = (TextView) convertView.findViewById(R.id.time_stamp_receiver);

            photoImageView_receiver.setVisibility(View.VISIBLE);
            messageTextView_receiver.setText(message.getmMessages());

            timeStampTextView.setText(Helpers.convertUnixTime(time, Helpers.DATE_STYLE_ONLY_TIME));

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
