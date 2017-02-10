package com.example.indraaguslesmana.wachat.Utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.indraaguslesmana.wachat.R;

/**
 * Created by indraaguslesmana on 2/10/17.
 */

public class Helpers {
    private static ProgressDialog mProgressDialog;


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
}
