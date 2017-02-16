package com.example.indraaguslesmana.wachat;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.indraaguslesmana.wachat.Utility.Constant;
import com.example.indraaguslesmana.wachat.Utility.Helpers;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
import com.example.indraaguslesmana.wachat.activity.LoginActivity;
import com.example.indraaguslesmana.wachat.activity.MainActivity;
import com.example.indraaguslesmana.wachat.model.Fcm;
import com.example.indraaguslesmana.wachat.model.UserContact;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

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
    public static FirebaseAuth.AuthStateListener mAuthListener;
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

        Log.d(TAG, "onCreate: " + mDatabaseReferenceUSER.toString());

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //getUserProfile

                if (user != null) {
                    // User is signed in
                    String token = FirebaseInstanceId.getInstance().getToken();

                    String userMail = user.getEmail();
                    String userName = "new User1";
                    String userId = user.getUid();

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    UserContact userContact = new UserContact(userMail, 0, userName, userId);
                    Fcm userFcm = new Fcm(Boolean.TRUE.toString(), token);
                    PreferenceUtils.setUserSession(userContact);

                    //set users data
                    mDatabaseReferenceUSER.child(userId).setValue(userContact,
                            new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError,
                                                       DatabaseReference databaseReference) {

                                    if (databaseError != null){
                                        Toast.makeText(WaChat.this, databaseError.toString(),
                                                Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                    //set fcm data
                    mDatabaseReferenceFCM.child(userId).setValue(userFcm);

                    Helpers.hideProgressDialog();

                    Intent intent = new Intent(WaChat.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out tea");
                }
                // [START_EXCLUDE]
                // TODO : redirect to home screen
                // updateUI(user);
                // [END_EXCLUDE]

            }
        };

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

    public FirebaseAuth.AuthStateListener getmAuthListener() {
        return mAuthListener;
    }
}
