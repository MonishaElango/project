package com.hertzai.hevolve.helpers;

import android.util.Log;

import com.hertzai.hevolve.models.appModel.HevolveApp;

public class HevolveLog {

    public static void e(String TAG, String MESSAGE) {
        if (HevolveApp.DEBUG)
            Log.e(TAG, MESSAGE);
    }

    public static void d(String TAG, String MESSAGE) {
        if (HevolveApp.DEBUG)
            Log.d(TAG, MESSAGE);
    }

    public static void i(String TAG, String MESSAGE) {
        if (HevolveApp.DEBUG)
            Log.i(TAG, MESSAGE);
    }

    public static void v(String TAG, String MESSAGE) {
        if (HevolveApp.DEBUG)
            Log.v(TAG, MESSAGE);
    }

    public static void w(String TAG, String MESSAGE) {
        if (HevolveApp.DEBUG)
            Log.w(TAG, MESSAGE);
    }
}
