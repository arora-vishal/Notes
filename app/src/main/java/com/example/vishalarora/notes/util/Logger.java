package com.example.vishalarora.notes.util;

import android.util.Log;

import com.example.vishalarora.notes.BuildConfig;

/**
 * Created by vishalarora on 25/03/18.
 */

public class Logger {

    private static final String TAG = Logger.class.getName();

    private static final boolean DEBUG = false;

    public static void log(String tag, Object object) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, "" + object);
        }
    }

    public static void log(Object object) {
        log(TAG, object);
    }

    public static void logException(String tag, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
            Log.e(tag, throwable.getMessage());
        }
    }

    public static void logException(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }
    }
}
