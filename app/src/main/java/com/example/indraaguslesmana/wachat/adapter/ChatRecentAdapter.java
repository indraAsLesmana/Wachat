package com.example.indraaguslesmana.wachat.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.Helpers;
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

        TextView recent_chatName = (TextView) convertView.findViewById(R.id.recent_chat_name);
        TextView recent_chatMessage = (TextView) convertView.findViewById(R.id.recent_chat_message);
        TextView recent_chatTime = (TextView) convertView.findViewById(R.id.recent_time);
        ImageView recent_chatProfileImage = (ImageView) convertView.findViewById(R.id.recent_chat_image);

        Chat_recent_model primary_recent_chat = getItem(position);

        if (primary_recent_chat != null) {
            recent_chatName.setText(primary_recent_chat.getName());
            recent_chatMessage.setText(primary_recent_chat.getMessage());

            if (primary_recent_chat.getTime_stamp() != null){
                String timeResult = primary_recent_chat.getTime_stamp().toString();
                long time = Long.valueOf(timeResult);
                timeResult = Helpers.convertUnixTime(time, Helpers.DATE_STYLE_ONLY_TIME);
                recent_chatTime.setText(timeResult);
            }

            recent_chatProfileImage.setImageResource(R.drawable.user_pacific);
        }

        return convertView;
    }
}
