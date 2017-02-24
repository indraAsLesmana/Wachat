package com.example.indraaguslesmana.wachat.Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.indraaguslesmana.wachat.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by indraaguslesmana on 2/10/17.
 */

public class Helpers {
    private static ProgressDialog mProgressDialog;
    private static final String TAG = "Helpers";


    public static void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public static String convertUnixTime(long unixSeconds) {
        Date date = new Date(unixSeconds); // *1000 is to convert seconds to milliseconds, dont mutiple * 1000 in firebase
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE HH:mm"); // the format of your date
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC+07:00"));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
