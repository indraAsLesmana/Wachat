package com.example.indraaguslesmana.wachat;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.indraaguslesmana.wachat.Utility.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by indraaguslesmana on 2/8/17.
 */

public class WaChat extends Application {

    private final String TAG = WaChat.class.getSimpleName();

    public static SharedPreferences sPreferences;

    // [START declare_auth]
    private static FirebaseAuth sAuth;
    // [END declare_auth]

    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mDatabaseReferenceUSER;
    private static DatabaseReference mDatabaseReferenceCHAT;
    private static DatabaseReference mDatabaseReferenceFCM;
    private static DatabaseReference mDatabaseReferenceGLOBALMESSAGES;

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    @Override
    public void onCreate() {
        super.onCreate();
        sPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        // [START initialize_auth]
        sAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReferenceUSER =
                mFirebaseDatabase.getReference().child(Constant.KEY_USER);
        mDatabaseReferenceCHAT =
                mFirebaseDatabase.getReference().child(Constant.KEY_CHAT);
        mDatabaseReferenceFCM =
                mFirebaseDatabase.getReference().child(Constant.KEY_FCM);
        mDatabaseReferenceGLOBALMESSAGES =
                mFirebaseDatabase.getReference().child(Constant.KEY_GLOBALMESSAGES);




        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
//                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
    }

    public static FirebaseAuth getsAuth() {
        return sAuth;
    }

    public static FirebaseDatabase getmFirebaseDatabase() {
        return mFirebaseDatabase;
    }

    public static DatabaseReference getmDatabaseReferenceUSER() {
        return mDatabaseReferenceUSER;
    }

    public static DatabaseReference getmDatabaseReferenceCHAT() {
        return mDatabaseReferenceCHAT;
    }

    public static DatabaseReference getmDatabaseReferenceFCM() {
        return mDatabaseReferenceFCM;
    }

    public static DatabaseReference getmDatabaseReferenceGLOBALMESSAGES() {
        return mDatabaseReferenceGLOBALMESSAGES;
    }
}
