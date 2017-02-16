package com.example.indraaguslesmana.wachat.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.indraaguslesmana.wachat.WaChat;
import com.example.indraaguslesmana.wachat.model.Fcm;
import com.example.indraaguslesmana.wachat.model.UserContact;

/**
 * Created by indraaguslesmana on 2/10/17.
 */

public class PreferenceUtils {
    private static final String TAG = PreferenceUtils.class.getSimpleName();

    public static final String PREFERENCE_USER_MAIL = "user-mail";
    public static final String PREFERENCE_USER_NAME = "user-name";
    public static final String PREFERENCE_USER_ID = "user-id";
    public static final String PREFERENCE_LAST_SEEN = "user-last-seen";

    public static final String PREFERENCE_USER_TOKEN ="user-token";
    public static final String PREFERENCE_DEFAULT = "null";


    public static void setUserFcm (Fcm fcm){
        SharedPreferences.Editor editor = WaChat.sPreferences.edit();
        editor.putString(PREFERENCE_USER_TOKEN, fcm.getToken());
        editor.apply();
    }

    public static void destroyUserFcm (){
        SharedPreferences.Editor editor = WaChat.sPreferences.edit();
        editor.remove(PREFERENCE_USER_TOKEN);
        editor.apply();
    }

    public static void setUserSession(UserContact userContact) {
        SharedPreferences.Editor editor = WaChat.sPreferences.edit();
        editor.putString(PREFERENCE_USER_MAIL, userContact.getEmail());
        editor.putString(PREFERENCE_USER_NAME, userContact.getName());
        editor.putString(PREFERENCE_USER_ID, userContact.getUid());
        editor.apply();
    }

    public static void destroyUserSession() {
        SharedPreferences.Editor editor = WaChat.sPreferences.edit();
        editor.remove(PREFERENCE_USER_MAIL);
        editor.remove(PREFERENCE_USER_NAME);
        editor.remove(PREFERENCE_USER_ID);
        editor.apply();
    }

    //Helper for get single preference
    public static String getSinglePrefrence (Context context, String prefereceName){
        String result = PREFERENCE_DEFAULT;
        SharedPreferences dataPreferece = WaChat.sPreferences;
        switch (prefereceName){
            case PREFERENCE_USER_TOKEN:
                result = dataPreferece.getString(PREFERENCE_USER_TOKEN, PREFERENCE_DEFAULT);
                Log.d(TAG, "getSinglePrefrence: " + result);
                break;
            case PREFERENCE_USER_ID:
                result = dataPreferece.getString(PREFERENCE_USER_ID, PREFERENCE_DEFAULT);
                Log.d(TAG, "getSinglePrefrence: " + result);
                break;
        }

        return result;
    }
}
