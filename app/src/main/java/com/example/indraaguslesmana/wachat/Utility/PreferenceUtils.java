package com.example.indraaguslesmana.wachat.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.indraaguslesmana.wachat.WaChat;
import com.example.indraaguslesmana.wachat.model.UserContact;

/**
 * Created by indraaguslesmana on 2/10/17.
 */

public class PreferenceUtils {
    private static final String PREFERENCE_USER_MAIL = "user-mail";
    private static final String PREFERENCE_USER_NAME = "user-name";
    private static final String PREFERENCE_USER_ID = "user-id";
    private static final String PREFERENCE_LAST_SEEN = "user-last-seen";

    private static final String PREFERENCE_USER_TOKEN ="user-token";
    private static final String PREFERENCE_DEFAULT = "null";


    public static void setUserSession(UserContact userContact) {
        SharedPreferences.Editor editor = WaChat.sPreferences.edit();
        editor.putString(PREFERENCE_USER_MAIL, userContact.getEmail());
        editor.putString(PREFERENCE_USER_NAME, userContact.getName());
        editor.putString(PREFERENCE_USER_ID, userContact.getUid());
        editor.commit();
    }

    public static void destroyUserSession() {
        SharedPreferences.Editor editor = WaChat.sPreferences.edit();
        editor.remove(PREFERENCE_USER_MAIL);
        editor.remove(PREFERENCE_USER_NAME);
        editor.remove(PREFERENCE_USER_ID);
        editor.commit();
    }

    //Helper for get single preference
    public static String getSinglePrefrence (Context context, String prefereceName){
        String result = PREFERENCE_DEFAULT;
        WaChat.sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        switch (prefereceName){
            case PREFERENCE_USER_TOKEN:
                result = WaChat.sPreferences.getString(PREFERENCE_USER_TOKEN, PREFERENCE_DEFAULT);
                break;

        }

        return result;
    }
}
